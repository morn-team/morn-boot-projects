package site.morn.template.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * 模板
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/10
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Template {

  /**
   * 模板类型
   */
  String type() default "";

  /**
   * 模板类型
   */
  @AliasFor("name")
  String value() default "";

  /**
   * 模板名称
   */
  @AliasFor("value")
  String name() default "";
}
