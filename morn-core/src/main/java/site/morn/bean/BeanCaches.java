package site.morn.bean;

import java.util.List;
import org.springframework.util.Assert;

/**
 * 实例缓存工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/10
 */
public class BeanCaches {

  /**
   * 默认实例缓存
   */
  private static IdentifiedBeanCache defaultBeanCache;

  private BeanCaches() {
  }

  /**
   * 初始化实例缓存
   *
   * @param beanCache 实例缓存
   */
  public static void initialize(IdentifiedBeanCache beanCache) {
    BeanCaches.defaultBeanCache = beanCache;
  }

  /**
   * 获取默认实例缓存
   *
   * @return 默认实例缓存
   */
  public static IdentifiedBeanCache defaultBeanCache() {
    Assert.notNull(defaultBeanCache, "尚未注入默认实例缓存");
    return defaultBeanCache;
  }

  /**
   * 按标识检索实例
   *
   * @param identify 标识
   * @param <T> 实例类型
   * @return 实例
   */
  public static <T> T bean(Class<T> type, AnnotationIdentify identify) {
    return defaultBeanCache().bean(type, identify);
  }

  /**
   * 按名称检索实例
   *
   * @param type 实例类
   * @param name 名称
   * @param <T> 实例类型
   * @return 实例
   */
  public static <T> T nameBean(Class<T> type, String name) {
    return defaultBeanCache().nameBean(type, name);
  }

  /**
   * 按标签检索实例
   *
   * @param tags 标签
   * @param <T> 实例类型
   * @return 实例
   */
  public static <T> T tagBean(Class<T> type, String... tags) {
    return defaultBeanCache().tagBean(type, tags);
  }

  /**
   * 按目标检索实例
   *
   * @param type 实例类
   * @param target 目标
   * @param <T> 实例类型
   * @return 实例对象
   */
  public static <T> T targetBean(Class<T> type, Class<?> target) {
    return defaultBeanCache().targetBean(type, target);
  }

  /**
   * 按标识检索实例
   *
   * @param type 实例类
   * @param identify 标识
   * @param <T> 实例类型
   * @return 实例集合
   */
  public static <T> List<T> beans(Class<T> type, AnnotationIdentify identify) {
    return defaultBeanCache().beans(type, identify);
  }

  /**
   * 按标签检索实例
   *
   * @param type 实例类
   * @param tags 标签
   * @param <T> 实例类型
   * @return 实例集合
   */
  public static <T> List<T> tagBeans(Class<T> type, String... tags) {
    return defaultBeanCache().tagBeans(type, tags);
  }

  /**
   * 按目标检索实例
   *
   * @param type 实例类
   * @param target 目标
   * @param <T> 实例类型
   * @return 实例集合
   */
  public static <T> List<T> targetBeans(Class<T> type, Class<?> target) {
    return defaultBeanCache().targetBeans(type, target);
  }

  /**
   * @deprecated {@link #nameBean(Class, String)}
   */
  @Deprecated
  public static <T> T bean(Class<T> type, String name) {
    return defaultBeanCache().bean(type, name);
  }

  /**
   * @deprecated {@link #targetBean(Class, Class)}
   */
  @Deprecated
  public static <T> T bean(Class<T> type, Class<?> target) {
    return defaultBeanCache().bean(type, target);
  }

  /**
   * @deprecated {@link #tagBeans(Class, String...)}
   */
  @Deprecated
  public static <T> List<T> beans(Class<T> type, String... tags) {
    return defaultBeanCache().beans(type, tags);
  }

  /**
   * @deprecated {@link #targetBeans(Class, Class)}
   */
  @Deprecated
  public static <T> List<T> beans(Class<T> type, Class<?> target) {
    return defaultBeanCache().beans(type, target);
  }
}
