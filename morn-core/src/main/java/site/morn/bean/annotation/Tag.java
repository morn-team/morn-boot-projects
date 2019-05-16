package site.morn.bean.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标签注解
 *
 * <p>标签是可重复的，否则请使用名称{@link Name}
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/19
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Tag {

  /**
   * 获取名称标签
   *
   * @return 名称标签
   */
  String[] value() default {};
}
