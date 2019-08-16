package site.morn.bean.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 函数注解
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(ElementType.METHOD)
public @interface Function {

  /**
   * 设置函数名称
   *
   * @return 函数名称
   */
  String value() default "";
}
