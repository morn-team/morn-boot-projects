package site.morn.bean;

/**
 * 注解标识
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
public interface AnnotationIdentify {


  /**
   * 获取名称
   *
   * @return 名称
   * @see site.morn.bean.annotation.Name
   */
  String getName();

  /**
   * 获取名称标签
   *
   * @return 名称标签
   * @see site.morn.bean.annotation.Tag
   */
  String[] getTags();

  /**
   * 获取目标
   *
   * @return 目标
   * @see site.morn.bean.annotation.Target
   */
  Class<?> getTarget();
}
