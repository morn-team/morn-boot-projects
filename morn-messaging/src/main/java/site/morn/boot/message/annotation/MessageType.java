package site.morn.boot.message.annotation;

import static site.morn.util.AnnotationIdentifyUtils.WILDCARD;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 消息类型
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/9
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageType {

  /**
   * 消息类型
   *
   * <p>默认监听所有类型
   */
  String[] value() default WILDCARD;
}
