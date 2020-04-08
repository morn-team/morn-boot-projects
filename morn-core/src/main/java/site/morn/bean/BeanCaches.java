package site.morn.bean;

import java.util.List;
import org.springframework.beans.factory.BeanFactory;
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
  private static BeanCache beanCache;

  /**
   * Spring实例工厂
   */
  private static BeanFactory beanFactory;

  private BeanCaches() {
  }

  /**
   * 初始化实例缓存
   *
   * @param beanCache 实例缓存
   */
  public static void initialize(BeanCache beanCache, BeanFactory beanFactory) {
    BeanCaches.beanCache = beanCache;
    BeanCaches.beanFactory = beanFactory;
  }

  /**
   * 获取默认实例缓存
   *
   * @return 默认实例缓存
   */
  public static BeanCache beanCache() {
    Assert.notNull(beanCache, "尚未注入默认实例缓存");
    return beanCache;
  }

  /**
   * 获取默认实例工厂
   *
   * @return 默认实例工厂
   */
  public static BeanFactory beanFactory() {
    Assert.notNull(beanFactory, "尚未注入默认实例工厂");
    return beanFactory;
  }

  /**
   * 按标识检索实例
   *
   * @param <T> 实例类型
   * @return 实例
   */
  public static <T> T bean(Class<T> type) {
    return beanCache().tagBean(type);
  }

  /**
   * 按标识检索实例
   *
   * @param identify 标识
   * @param <T> 实例类型
   * @return 实例
   */
  public static <T> T bean(Class<T> type, AnnotationIdentify identify) {
    return beanCache().bean(type, identify);
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
    return beanCache().nameBean(type, name);
  }

  /**
   * 按标签检索实例
   *
   * @param tags 标签
   * @param <T> 实例类型
   * @return 实例
   */
  public static <T> T tagBean(Class<T> type, String... tags) {
    return beanCache().tagBean(type, tags);
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
    return beanCache().targetBean(type, target);
  }

  /**
   * 按标识检索实例
   *
   * @param type 实例类
   * @param <T> 实例类型
   * @return 实例集合
   */
  public static <T> List<T> beans(Class<T> type) {
    return beanCache().tagBeans(type);
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
    return beanCache().beans(type, identify);
  }

  /**
   * 按标识检索实例持有者
   *
   * @param type 实例类
   * @param identify 标识
   * @param <T> 实例类型
   * @return 实例集合
   */
  public static <T> List<IdentifiedBeanHolder<T>> beanHolders(Class<T> type,
      AnnotationIdentify identify) {
    return beanCache().beanHolders(type, identify);
  }

  /**
   * 按标识检索函数
   *
   * @param beanIdentify 实例标识
   * @param functionIdentify 函数标识
   * @return 函数集合
   */
  public static List<FunctionHolder> functions(AnnotationIdentify beanIdentify,
      AnnotationIdentify functionIdentify) {
    return beanCache().functions(beanIdentify, functionIdentify);
  }

  /**
   * 按标识检索函数
   *
   * @param holders 实例持有者集合
   * @param functionIdentify 函数标识
   * @return 函数集合
   */
  public static <T> List<FunctionHolder> functions(
      List<IdentifiedBeanHolder<T>> holders,
      AnnotationIdentify functionIdentify) {
    return beanCache().functions(holders, functionIdentify);
  }

  /**
   * 按标识检索函数
   *
   * @param functionIdentify 函数标识
   * @return 函数集合
   */
  public static List<FunctionHolder> functions(AnnotationIdentify functionIdentify) {
    return beanCache().functions(functionIdentify);
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
    return beanCache().tagBeans(type, tags);
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
    return beanCache().targetBeans(type, target);
  }

  /**
   * 获取Spring容器中的实例
   *
   * <p>不受{@link BeanCache}管理
   *
   * @param name 实例名称
   * @param requiredType 实例类
   * @param <T> 实例类型
   * @return 实例
   * @see BeanFactory#getBean(String, Class)
   */
  public static <T> T getBean(String name, Class<T> requiredType) {
    return beanFactory.getBean(name, requiredType);
  }

  /**
   * 获取Spring容器中的实例
   *
   * <p>不受{@link BeanCache}管理
   *
   * @param requiredType 实例类
   * @param <T> 实例类型
   * @return 实例
   * @see BeanFactory#getBean(Class)
   */
  public static <T> T getBean(Class<T> requiredType) {
    return beanFactory.getBean(requiredType);
  }

  /**
   * 获取Spring容器中的实例
   *
   * <p>不受{@link BeanCache}管理
   *
   * @param requiredType 实例类
   * @param args 构造参数
   * @param <T> 实例类型
   * @return 实例
   * @see BeanFactory#getBean(Class, Object...)
   */
  public static <T> T getBean(Class<T> requiredType, Object... args) {
    return beanFactory.getBean(requiredType, args);
  }
}
