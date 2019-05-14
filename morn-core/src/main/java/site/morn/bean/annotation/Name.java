package site.morn.bean.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 命名注解
 *
 * <p>名称是唯一的，否则请使用标签注解{@link Tag}
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

  /**
   * 获取名称
   *
   * @return 名称
   */
  String value();
}
