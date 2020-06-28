package site.morn.cipher.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 算法名称
 *
 * @author timely-rain
 * @since 1.2.0, 2020/4/24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AlgorithmName {

  /**
   * 算法名称
   */
  String value() default "";
}
