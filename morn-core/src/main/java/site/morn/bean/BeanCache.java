package site.morn.bean;

import java.util.List;

/**
 * 特征实例缓存
 *
 * <p>缓存已标识特征的实例对象，提供简洁的检索API
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
public interface BeanCache {

  /**
   * 获取所有实例持有者
   *
   * @return 实例持有者集合
   */
  <T> List<BeanHolder<T>> getAllHolders();

  /**
   * 获取实例持有者
   *
   * @param type 实例类
   * @return 实例持有者集合
   */
  <T> List<BeanHolder<T>> getHolders(Class<T> type);

  /**
   * 存储实例持有者
   *
   * @param beanName 实例名称
   * @param holder   实例持有者
   * @param <T>      检索类型
   */
  <T> void put(String beanName, BeanHolder<T> holder);
}
