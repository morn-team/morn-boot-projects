package site.morn.bean;

import site.morn.bean.annotation.Tag;

/**
 * 注解名称
 *
 * <p>名称是唯一的，否则请使用标记{@link Tag}
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
public interface AnnotationName {

  /**
   * 获取名称
   *
   * @return 名称
   */
  String getName();
}
