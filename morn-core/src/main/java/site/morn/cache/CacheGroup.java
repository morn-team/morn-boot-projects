package site.morn.cache;

import java.util.concurrent.Callable;
import org.springframework.lang.Nullable;

/**
 * 分类缓存
 *
 * @author timely-rain
 * @since 1.2.1, 2019/9/4
 */
public interface CacheGroup {

  /**
   * 清除缓存
   *
   * @param group 组
   * @param key 键
   */
  void clear(String group, String key);

  /**
   * 清除缓存
   */
  void clearAll();

  /**
   * 清除缓存
   *
   * @param group 组
   */
  void clearGroup(String group);

  /**
   * 清除缓存
   *
   * @param key 键
   */
  void clearKey(String key);

  /**
   * 获取缓存
   *
   * @param group 组
   * @param key 键
   * @return 值
   */
  @Nullable
  Object get(String group, String key);

  /**
   * 获取缓存
   *
   * @param group 组
   * @param key 键
   * @param <T> 值泛型
   * @param type 值类型
   * @return 值
   */
  @Nullable
  <T> T get(String group, String key, @Nullable Class<T> type);

  /**
   * 获取缓存
   *
   * @param group 组
   * @param key 键
   * @param valueLoader 缺省值
   * @param <T> 值类型
   * @return 值
   */
  @Nullable
  <T> T get(String group, String key, Callable<T> valueLoader);

  /**
   * 更新缓存
   *
   * @param group 类别
   * @param key 键
   * @param value 值
   */
  void put(String group, String key, @Nullable Object value);

  /**
   * 软更新缓存
   *
   * @param group 组
   * @param key 键
   * @param value 缺省值
   * @param <T> 值类型
   * @return 实际值
   */
  <T> T putIfAbsent(String group, String key, @Nullable T value);

  /**
   * 设置缓存名称
   */
  void setCacheName(String cacheName);
}
