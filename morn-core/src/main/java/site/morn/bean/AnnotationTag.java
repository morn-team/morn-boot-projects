package site.morn.bean;

import site.morn.bean.annotation.Name;

/**
 * 注解标签
 *
 * <p>标签是可重复的，否则请使用名称{@link Name}
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/21
 */
public interface AnnotationTag {

  /**
   * 获取名称标签
   *
   * @return 名称标签
   */
  String[] getTags();
}
