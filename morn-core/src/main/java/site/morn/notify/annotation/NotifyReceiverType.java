package site.morn.notify.annotation;


import static site.morn.util.AnnotationFeatureUtils.WILDCARD;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通知接受人类型注解
 * <p>用于标识通知的适配器{@link site.morn.notify.NotifyMetaAdapter}、处理者{@link
 * site.morn.notify.NotifyProcessor}等</p>
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/19
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotifyReceiverType {

  /**
   * 通知接受人类型
   */
  String value() default WILDCARD;
}
