package site.morn.bean;

/**
 * 注解特征
 *
 * <p>注解特征用于类或方法的唯一标识、标签、用途
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
public interface AnnotationFeature {

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
   * 获取源
   *
   * @return 源
   * @see site.morn.bean.annotation.Source
   */
  Class<?> getSource();

  /**
   * 获取目标
   *
   * @return 目标
   * @see site.morn.bean.annotation.Target
   */
  Class<?> getTarget();
}
