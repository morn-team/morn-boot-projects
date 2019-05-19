package site.morn.bean;

/**
 * 注解标识
 *
 * <p>注解标识用于描述对象的唯一标识、标签、用途
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
public interface AnnotationIdentify {

  /**
   * 获取名称
   *
   * <p>同类中不允许名称重复
   *
   * @return 名称
   * @see site.morn.bean.annotation.Name
   * @see site.morn.bean.annotation.Function
   */
  String getName();

  /**
   * 获取标签
   *
   * <p>同类中允许标签重复
   *
   * @return 标签
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
