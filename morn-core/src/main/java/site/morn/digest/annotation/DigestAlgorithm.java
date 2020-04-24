package site.morn.digest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据算法
 *
 * @author timely-rain
 * @since 1.2.0, 2020/4/24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DigestAlgorithm {

  /**
   * 算法类型
   */
  String value() default "";
}
