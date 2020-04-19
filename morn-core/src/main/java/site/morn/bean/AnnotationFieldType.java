package site.morn.bean;

/**
 * 注解属性类型
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/13
 */
public enum AnnotationFieldType {
  /**
   * 命名注解
   *
   * @see site.morn.bean.annotation.Name
   */
  NAME,
  /**
   * 源注解
   */
  SOURCE,
  /**
   * 标签注解
   *
   * @see site.morn.bean.annotation.Tag
   */
  TAG,
  /**
   * 目标注解
   *
   * @see site.morn.bean.annotation.Target
   */
  TARGET
}
