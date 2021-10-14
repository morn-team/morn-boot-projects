package site.morn.boot.log;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import site.morn.bean.BeanPool;
import site.morn.bean.support.Tags;
import site.morn.log.OperateAction;
import site.morn.log.OperateArguments;
import site.morn.log.OperateGroup;
import site.morn.log.OperateMeta;
import site.morn.log.OperateMode;
import site.morn.log.Operation;
import site.morn.log.OperationAdapter;
import site.morn.log.OperationContext;
import site.morn.log.OperationConverter;
import site.morn.log.OperationProcessor;
import site.morn.task.TaskExecutors;
import site.morn.util.BeanFunctionUtils;

/**
 * 操作切面
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Aspect
@Slf4j
public class OperateAspect {

  private final BeanPool beanPool;

  /**
   * 操作日志配置项
   */
  private final OperateProperties properties;

  public OperateAspect(BeanPool beanPool, OperateProperties properties) {
    this.beanPool = beanPool;
    this.properties = properties;
  }

  /**
   * 日志切面
   */
  @Around("@annotation(site.morn.log.OperateAction)")
  public Object aroundOperate(ProceedingJoinPoint point) throws Throwable {
    OperateMeta meta = resolveOperatePoint(point);
    long startTime = System.currentTimeMillis(); // 起始时间
    try {
      // 执行目标方法，并记录执行结果
      Object returned = point.proceed();
      meta.setMethodReturned(returned);
      meta.setSuccess(true);
      return returned;
    } catch (Throwable throwable) {
      // 执行失败，记录异常信息
      meta.setSuccess(false);
      meta.setThrowable(throwable);
      throw throwable;
    } finally {
      long endTime = System.currentTimeMillis(); // 结束时间
      long duration = endTime - startTime; // 消耗时间
      // 读取操作日志参数
      meta.setStartTime(startTime);
      meta.setEndTime(endTime);
      meta.setDuration(duration);
      // 获取实时参数
      meta.setCodeArgs(OperateArguments.getAll().toArray());
      OperateArguments.clear();
      OperationContext context = buildContext(meta);
      if (properties.isAsync()) {
        extractOperationAsync(context);
      } else {
        extractOperation(context);
      }
    }
  }

  /**
   * 解析操作日志切点
   *
   * @param point 切点
   * @return 操作日志构建器
   */
  private OperateMeta resolveOperatePoint(ProceedingJoinPoint point) {
    Class<?> targetClass = point.getTarget().getClass();
    MethodSignature methodSignature = (MethodSignature) point.getSignature();
    String className = targetClass.getName();
    String methodName = methodSignature.getName();
    log.debug("Operate|Method:{}#{}", className, methodName);
    // 获取操作组注解
    OperateGroup operateGroup = AnnotationUtils.findAnnotation(targetClass, OperateGroup.class);
    // 获取操作行为注解
    OperateAction operateAction = AnnotationUtils
        .findAnnotation(methodSignature.getMethod(), OperateAction.class);
    Assert.notNull(operateAction, "Operate|无法获取[@OperateAction]:" + targetClass.getSimpleName());
    // 构建操作日志元数据
    OperateMeta meta = new OperateMeta();
    meta.setSource(point);
    meta.setMethodArgs(point.getArgs());
    if (Objects.nonNull(operateGroup)) {
      meta.setGroupName(operateGroup.value());
      meta.setGroupArgs(operateGroup.args());
    }
    meta.setActionName(operateAction.value());
    meta.setActionArgs(operateAction.args());
    meta.setExcludeNames(operateAction.excludeNames());
    meta.setExcepts(operateAction.excepts());
    return meta;
  }

  /**
   * 构建操作日志上下文
   *
   * @param operateMeta 操作日志元数据
   * @return 操作日志上下文
   */
  private OperationContext buildContext(OperateMeta operateMeta) {
    // 将操作日志元数据，转换为操作日志实例
    String[] tags = Tags.from(OperateMode.class, properties.getMode()).toArray();
    Operation operation = BeanFunctionUtils
        .convert(OperationConverter.class, operateMeta, tags);
    // 对操作上下文进行场景适配
    OperationContext context = new OperationContext(operateMeta, operation);
    context = BeanFunctionUtils.adaptions(OperationAdapter.class, context, tags);
    return context;
  }

  /**
   * 提取操作信息-异步
   */
  private void extractOperationAsync(OperationContext context) {
    CompletableFuture<?> future = TaskExecutors
        .submit(() -> extractOperation(context));
    future.exceptionally(throwable -> {
      log.warn(
          "Operate|Error|Async:" + throwable.getClass().getName() + ":" + throwable.getMessage(),
          throwable);
      return null;
    });
  }

  /**
   * 提取操作信息
   */
  private void extractOperation(OperationContext context) {
    // 处理操作日志
    List<OperationProcessor> processors = beanPool.tagBeans(OperationProcessor.class);
    Assert.notEmpty(processors, "Operate|Error|请注册操作日志处理器:" + OperationProcessor.class.getName());
    for (OperationProcessor processor : processors) {
      processor.handle(context.getMeta(), context.getOperation());
    }
  }
}
