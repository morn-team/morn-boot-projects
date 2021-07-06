package site.morn.boot.log;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import site.morn.bean.BeanCache;
import site.morn.bean.support.Tags;
import site.morn.log.OperateAction;
import site.morn.log.OperateArguments;
import site.morn.log.OperateGroup;
import site.morn.log.OperateMeta;
import site.morn.log.OperateMeta.OperateMetaBuilder;
import site.morn.log.OperateMode;
import site.morn.log.Operation;
import site.morn.log.OperationConverter;
import site.morn.log.OperationProcessor;
import site.morn.task.ListenableFutureDispatcher;
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

  private final BeanCache beanCache;

  /**
   * 操作日志配置项
   */
  private final OperateProperties properties;

  public OperateAspect(BeanCache beanCache, OperateProperties properties) {
    this.beanCache = beanCache;
    this.properties = properties;
  }

  /**
   * 日志切面
   */
  @Around("@annotation(site.morn.log.OperateAction)")
  public Object aroundOperate(ProceedingJoinPoint point) throws Throwable {
    OperateMetaBuilder operateMetaBuilder = resolveOperatePoint(point);
    operateMetaBuilder.source(point); // 记录日志来源
    long startTime = System.currentTimeMillis(); // 起始时间
    try {
      // 执行目标方法，并记录执行结果
      Object returned = point.proceed();
      operateMetaBuilder.methodReturned(returned);
      operateMetaBuilder.success(true);
      return returned;
    } catch (Throwable throwable) {
      // 执行失败，记录异常信息
      operateMetaBuilder.success(false);
      operateMetaBuilder.throwable(throwable);
      throw throwable;
    } finally {
      long endTime = System.currentTimeMillis(); // 结束时间
      long duration = endTime - startTime; // 消耗时间
      // 读取操作日志参数
      operateMetaBuilder
          .startTime(startTime)
          .endTime(endTime)
          .duration(duration)
          .actionArgs(OperateArguments.getAll().toArray());
      OperateArguments.clear();
      if (properties.isAsync()) {
        extractOperationAsync(operateMetaBuilder);
      } else {
        extractOperation(operateMetaBuilder);
      }
    }
  }

  /**
   * 解析操作日志切点
   *
   * @param point 切点
   * @return 操作日志构建器
   */
  private OperateMetaBuilder resolveOperatePoint(ProceedingJoinPoint point) {
    Class<?> targetClass = point.getTarget().getClass();
    // 获取操作组注解
    OperateGroup operateGroup = AnnotationUtils.findAnnotation(targetClass, OperateGroup.class);
    // 获取操作行为注解
    MethodSignature methodSignature = (MethodSignature) point.getSignature();
    OperateAction operateAction = AnnotationUtils
        .findAnnotation(methodSignature.getMethod(), OperateAction.class);
    Assert.notNull(operateAction, "无法获取操作行为：" + targetClass.getSimpleName());
    // 获取实时参数
    Object[] codeArgs = OperateArguments.getAll().toArray();

    // 构建操作日志元数据
    OperateMetaBuilder builder = OperateMeta.builder().actionArgs(operateAction.args())
        .codeArgs(codeArgs).excludeNames(operateAction.excludeNames()).methodArgs(point.getArgs())
        .mode(operateAction.mode()).name(operateAction.value()).excepts(operateAction.excepts());
    if (Objects.nonNull(operateGroup)) {
      builder.module(operateGroup.value());
      builder.groupArgs(operateGroup.args());
    }
    return builder;
  }

  /**
   * 提取操作信息-异步
   */
  private void extractOperationAsync(OperateMetaBuilder operateMetaBuilder) {
    ListenableFuture<Object> future = ListenableFutureDispatcher
        .submit(() -> extractOperation(operateMetaBuilder));
    future.completable().exceptionally(throwable -> {
      log.warn(throwable.getMessage(), throwable);
      return throwable;
    });
  }

  /**
   * 提取操作信息
   */
  private void extractOperation(OperateMetaBuilder operateMetaBuilder) {
    // 将操作日志元数据，转换为操作日志实例
    OperateMeta operateMeta = operateMetaBuilder.build();
    Tags tags = Tags.from(OperateMode.class, properties.getMode());
    Operation operation = BeanFunctionUtils
        .convert(OperationConverter.class, operateMeta, tags.toArray());
    // 处理操作日志
    List<OperationProcessor> processors = beanCache.tagBeans(OperationProcessor.class);
    Assert.notEmpty(processors, getStringSupplier(tags));
    for (OperationProcessor processor : processors) {
      processor.handle(operateMeta, operation);
    }
  }

  /**
   * 提示信息
   */
  private Supplier<String> getStringSupplier(Tags tags) {
    return () -> {
      String tagString = StringUtils.arrayToCommaDelimitedString(tags.toArray());
      return "请注册操作日志处理器：" + OperationProcessor.class.getName() + tagString;
    };
  }
}
