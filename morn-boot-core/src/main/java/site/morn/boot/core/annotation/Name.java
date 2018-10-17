package site.morn.boot.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.core.annotation.AliasFor;

/**
 * 命名注解
 *
 * @author timely-rain
 * @version 1.0.0, 2018/10/15
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

  /**
   * 获取名称
   *
   * @return 名称
   */
  @AliasFor("value")
  String name() default "";

  /**
   * 获取名称
   *
   * @return 名称
   */
  @AliasFor("name")
  String value() default "";
}
