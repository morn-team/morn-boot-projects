package site.morn.notify.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * 系统通知
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/9
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Notify {

  /**
   * 通知类型
   */
  String type() default "";

  /**
   * 通知名称
   */
  @AliasFor("value")
  String name() default "";

  /**
   * 通知名称
   */
  @AliasFor("name")
  String value() default "";
}
