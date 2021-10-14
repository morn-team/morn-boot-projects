package site.morn.bean;

import java.util.List;
import java.util.stream.Stream;
import site.morn.bean.support.AnnotationFeatureBuilder;

/**
 * 实例池
 * <p>
 * 从{@link BeanCache}获取特定实例对象，提供简洁的检索API
 *
 * @author timely-rain
 * @since 1.2.2, 2021/9/27
 */
public interface BeanPool {


  /**
   * 按特征检索实例
   *
   * @param feature 特征
   * @param <T>     实例类型
   * @return 实例
   */
  default <T> T bean(Class<T> type, AnnotationFeature feature) {
    List<T> beans = beans(type, feature);
    if (beans.isEmpty()) {
      return null;
    }
    return beans.get(0);
  }

  /**
   * 按名称检索实例
   *
   * @param name 名称
   * @param <T>  实例类型
   * @return 实例
   */
  default <T> T nameBean(Class<T> type, String name) {
    return bean(type, AnnotationFeatureBuilder.withName(name).build());
  }

  /**
   * 按标签检索实例
   *
   * @param tags 标签
   * @param <T>  实例类型
   * @return 实例
   */
  default <T> T tagBean(Class<T> type, String... tags) {
    return bean(type, AnnotationFeatureBuilder.withTags(tags).build());
  }

  /**
   * 按源检索实例
   *
   * @param source 源
   * @param <T>    实例类型
   * @return 实例对象
   */
  default <T> T sourceBean(Class<T> type, Class<?> source) {
    return bean(type, AnnotationFeatureBuilder.withSource(source).build());
  }

  /**
   * 按目标检索实例
   *
   * @param target 目标
   * @param <T>    实例类型
   * @return 实例对象
   */
  default <T> T targetBean(Class<T> type, Class<?> target) {
    return bean(type, AnnotationFeatureBuilder.withTarget(target).build());
  }

  /**
   * 按特征检索实例
   *
   * @param feature 特征
   * @param <T>     实例类型
   * @return 实例集合
   */
  <T> List<T> beans(Class<T> type, AnnotationFeature feature);

  /**
   * 按特征检索实例持有者
   *
   * @param type    实例类
   * @param feature 特征
   * @param <T>     实例类型
   * @return 实例集合
   */
  <T> List<BeanHolder<T>> beanHolders(Class<T> type, AnnotationFeature feature);

  /**
   * 按特征检索实例持有者
   *
   * @param type    实例类
   * @param feature 特征
   * @param <T>     实例类型
   * @return 实例流
   */
  <T> Stream<BeanHolder<T>> beanHolderStream(Class<T> type, AnnotationFeature feature);

  /**
   * 按标签检索实例
   *
   * @param tags 标签
   * @param <T>  实例类型
   * @return 实例集合
   */
  default <T> List<T> tagBeans(Class<T> type, String... tags) {
    return beans(type, AnnotationFeatureBuilder.withTags(tags).build());
  }

  /**
   * 按源检索实例
   *
   * @param source 源
   * @param <T>    实例类型
   * @return 实例集合
   */
  default <T> List<T> sourceBeans(Class<T> type, Class<?> source) {
    return beans(type, AnnotationFeatureBuilder.withSource(source).build());
  }

  /**
   * 按目标检索实例
   *
   * @param target 目标
   * @param <T>    实例类型
   * @return 实例集合
   */
  default <T> List<T> targetBeans(Class<T> type, Class<?> target) {
    return beans(type, AnnotationFeatureBuilder.withTarget(target).build());
  }
}
