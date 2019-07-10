package site.morn.bean;

import java.util.List;

/**
 * 标识的实例缓存
 *
 * <p>缓存已标识的实例对象，提供简洁的检索API
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
public interface IdentifiedBeanCache {

  /**
   * 存储实例持有者
   *
   * @param holder 实例持有者
   * @param <T> 检索类型
   */
  <T> void cache(IdentifiedBeanHolder<T> holder);

  /**
   * 按标识检索实例
   *
   * @param identify 标识
   * @param <T> 实例类型
   * @return 实例
   */
  default <T> T bean(Class<T> type, AnnotationIdentify identify) {
    List<T> beans = beans(type, identify);
    if (beans.isEmpty()) {
      return null;
    }
    return beans.get(0);
  }

  /**
   * 按名称检索实例
   *
   * @param name 名称
   * @param <T> 实例类型
   * @return 实例
   */
  default <T> T nameBean(Class<T> type, String name) {
    AnnotationIdentifyCase identify = AnnotationIdentifyCase.builder().name(name).build();
    return bean(type, identify);
  }

  /**
   * 按标签检索实例
   *
   * @param tags 标签
   * @param <T> 实例类型
   * @return 实例
   */
  default <T> T tagBean(Class<T> type, String... tags) {
    AnnotationIdentifyCase identify = AnnotationIdentifyCase.builder().tags(tags).build();
    return bean(type, identify);
  }

  /**
   * 按目标检索实例
   *
   * @param target 目标
   * @param <T> 实例类型
   * @return 实例对象
   */
  default <T> T targetBean(Class<T> type, Class<?> target) {
    AnnotationIdentifyCase identify = AnnotationIdentifyCase.builder().target(target).build();
    return bean(type, identify);
  }

  /**
   * 按标识检索实例
   *
   * @param identify 标识
   * @param <T> 实例类型
   * @return 实例集合
   */
  <T> List<T> beans(Class<T> type, AnnotationIdentify identify);

  /**
   * 按标识检索实例持有者
   *
   * @param type 实例类
   * @param identify 标识
   * @param <T> 实例类型
   * @return 实例集合
   */
  <T> List<IdentifiedBeanHolder<T>> beanHolders(Class<T> type, AnnotationIdentify identify);

  /**
   * 按标识检索函数
   *
   * @param beanIdentify 实例标识
   * @param functionIdentify 函数标识
   * @return 函数集合
   */
  List<FunctionHolder> functions(AnnotationIdentify beanIdentify,
      AnnotationIdentify functionIdentify);


  /**
   * 按标识检索函数
   *
   * @param holders 实例持有者集合
   * @param functionIdentify 函数标识
   * @return 函数集合
   */
  <T> List<FunctionHolder> functions(List<IdentifiedBeanHolder<T>> holders,
      AnnotationIdentify functionIdentify);

  /**
   * 按标识检索函数
   *
   * @param functionIdentify 函数标识
   * @return 函数集合
   */
  default List<FunctionHolder> functions(AnnotationIdentify functionIdentify) {
    return functions((AnnotationIdentify) null, functionIdentify);
  }

  /**
   * 按标签检索实例
   *
   * @param tags 标签
   * @param <T> 实例类型
   * @return 实例集合
   */
  default <T> List<T> tagBeans(Class<T> type, String... tags) {
    AnnotationIdentifyCase identify = AnnotationIdentifyCase.builder().tags(tags).build();
    return beans(type, identify);
  }

  /**
   * 按目标检索实例
   *
   * @param target 目标
   * @param <T> 实例类型
   * @return 实例集合
   */
  default <T> List<T> targetBeans(Class<T> type, Class<?> target) {
    AnnotationIdentifyCase identify = AnnotationIdentifyCase.builder().target(target).build();
    return beans(type, identify);
  }
}
