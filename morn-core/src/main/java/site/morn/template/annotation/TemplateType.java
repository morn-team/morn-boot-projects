package site.morn.template.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模板类型注解
 * <p>用于标识模板解析器{@link site.morn.template.TemplateResolver}</p>
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateType {

  /**
   * 模板类型
   */
  String value() default "";
}
