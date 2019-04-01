package site.morn.boot.log;

import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.log.OperateAction;
import site.morn.log.OperateArguments;
import site.morn.log.OperateGroup;
import site.morn.log.OperateMeta;
import site.morn.log.OperateMeta.OperateMetaBuilder;
import site.morn.log.Operation;
import site.morn.log.OperationConverter;
import site.morn.log.OperationProcessor;

/**
 * 操作切面
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Aspect
@Slf4j
public class OperateAspect {

  private final IdentifiedBeanCache beanCache;

  public OperateAspect(IdentifiedBeanCache beanCache) {
    this.beanCache = beanCache;
  }

  /**
   * 创建切点
   */
  @Pointcut("@annotation(site.morn.log.OperateAction)")
  public void pointcut() {
  }

  @Around("pointcut()")
  public Object aroundOperate(ProceedingJoinPoint point) throws Throwable {
    OperateMetaBuilder operateMetaBuilder = resolveOperatePoint(point);
    operateMetaBuilder.source(point); // 记录日志来源
    try {
      // 执行目标方法，并记录执行结果
      Object returned = point.proceed();
      operateMetaBuilder.methodReturned(returned);
      operateMetaBuilder.success(true);
      return returned;
    } catch (Throwable throwable) {
      operateMetaBuilder.success(false);
      throw throwable;
    } finally {
      // 读取操作日志参数
      operateMetaBuilder.arguments(OperateArguments.getAll().toArray());
      OperateArguments.clear();
      // 将操作日志元数据，转换为操作日志实例
      OperateMeta operateMeta = operateMetaBuilder.build();
      List<OperationConverter> converters = beanCache.beans(OperationConverter.class);
      Assert.notEmpty(converters, "请注册操作日志转换器：" + OperationConverter.class.getName());
      Operation operation = converters.get(0).convert(operateMeta);
      // 处理操作日志
      List<OperationProcessor> processors = beanCache.beans(OperationProcessor.class);
      Assert.notEmpty(processors, "请注册操作日志处理器：" + OperationProcessor.class.getName());
      for (OperationProcessor processor : processors) {
        processor.handle(operateMeta, operation);
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
    // 获取操作组注解
    OperateGroup operateGroup = AnnotationUtils
        .findAnnotation(point.getTarget().getClass(), OperateGroup.class);
    // 获取操作行为注解
    MethodSignature methodSignature = (MethodSignature) point.getSignature();
    OperateAction operateAction = AnnotationUtils
        .findAnnotation(methodSignature.getMethod(), OperateAction.class);
    // 构建操作日志元数据
    OperateMetaBuilder builder = OperateMeta.builder().methodArgs(point.getArgs())
        .name(operateAction.value()).excepts(operateAction.excepts());
    if (Objects.nonNull(operateGroup)) {
      builder.module(operateGroup.value());
    }
    return builder;
  }
}
