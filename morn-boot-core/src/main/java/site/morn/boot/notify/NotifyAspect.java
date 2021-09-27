package site.morn.boot.notify;

import static site.morn.constant.ApplicationConstants.Notifies.BUILTIN_NOTIFY_ANNOTATION;
import static site.morn.constant.ApplicationConstants.Notifies.BUILTIN_NOTIFY_KEY;

import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import site.morn.bean.support.BeanCaches;
import site.morn.core.CriteriaMap;
import site.morn.notify.AnnotationNotifyMetaAdapter;
import site.morn.notify.NotifyDispatcher;
import site.morn.notify.NotifyMeta;
import site.morn.notify.annotation.Notify;
import site.morn.notify.annotation.NotifyReceiver;
import site.morn.notify.support.NotifyArguments;
import site.morn.template.TemplateMeta;
import site.morn.template.annotation.Template;

/**
 * 通知切面
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
@Slf4j
@Aspect
public class NotifyAspect {

  private final NotifyDispatcher notifyDispatcher;

  public NotifyAspect(NotifyDispatcher notifyDispatcher) {
    this.notifyDispatcher = notifyDispatcher;
  }

  /**
   * 创建切点
   */
  @Pointcut("@annotation(site.morn.notify.annotation.Notify)")
  public void pointcut() {
  }

  /**
   * 系统通知前置触发器
   *
   * @param point 切点
   */
  @Before("pointcut()")
  public void beforeTrigger(JoinPoint point) {
    Class<?> targetClass = point.getTarget().getClass();
    MethodSignature methodSignature = (MethodSignature) point.getSignature();
    // 获取通知注解
    Notify notify = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Notify.class);
    Assert.notNull(notify, "无法获取通知注解：" + targetClass.getSimpleName());
    CriteriaMap builtinArgs = NotifyArguments.getBuiltinArgs();
    builtinArgs.put(BUILTIN_NOTIFY_ANNOTATION, notify);
    String notifyKey = notify.type() + "." + notify.value();
    builtinArgs.put(BUILTIN_NOTIFY_KEY, notifyKey);
  }

  /**
   * 触发系统通知
   * <p>必须配置通知注解{@link Notify}和接收人注解{@link NotifyReceiver}</p>
   *
   * @param point 切点
   */
  @AfterReturning("pointcut()")
  public void triggering(JoinPoint point) {
    Class<?> targetClass = point.getTarget().getClass();
    MethodSignature methodSignature = (MethodSignature) point.getSignature();
    // 获取通知注解
    Notify notify = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Notify.class);
    Assert.notNull(notify, "无法获取通知注解：" + targetClass.getSimpleName());
    // 获取接收人注解
    NotifyReceiver receiver = AnnotationUtils
        .findAnnotation(methodSignature.getMethod(), NotifyReceiver.class);

    // 获取模板注解
    Template template = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Template.class);
    boolean hasTemplateAnnotation = Objects.nonNull(template);
    // 初始化通知元数据
    NotifyMeta notifyMeta = new NotifyMeta();
    TemplateMeta templateMeta = new TemplateMeta();
    // 从元数据适配器中完善元数据
    List<AnnotationNotifyMetaAdapter> adapters = BeanCaches
        .beans(AnnotationNotifyMetaAdapter.class);
    for (AnnotationNotifyMetaAdapter adapter : adapters) {
      adapter.setNotify(notify);
      adapter.setReceiver(receiver);
      adapter.setNotifyMeta(notifyMeta);
      notifyMeta = adapter.adaptionNotifyMeta();
      if (hasTemplateAnnotation) {
        adapter.setTemplate(template);
        adapter.setTemplateMeta(templateMeta);
        templateMeta = adapter.adaptionTemplateMeta();
      }
    }
    // 分发通知
    if (hasTemplateAnnotation) {
      notifyDispatcher.setTemplateMeta(templateMeta);
    }
    notifyDispatcher.handle(notifyMeta);
    clear();
  }

  /**
   * 系统通知触发后
   */
  @AfterThrowing("pointcut()")
  public void triggered() {
    clear();
  }

  /**
   * 清除冗余数据
   */
  private void clear() {
    // 清空线程变量
    NotifyArguments.clear();
    // 同一线城多次执行通知，会造成参数存留
    notifyDispatcher.setTemplateMeta(null);
  }
}
