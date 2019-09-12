package site.morn.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.morn.util.TypeUtils;

/**
 * 默认分类缓存
 *
 * @author timely-rain
 * @since 1.2.1, 2019/9/5
 */
public class SimpleCacheGroup implements CacheGroup {

  /**
   * 组名称
   */
  private static final String GROUPS = "_groups";

  /**
   * 缓存管理器
   */
  private final CacheManager cacheManager;

  /**
   * 缓存名称
   */
  private String cacheName;

  public SimpleCacheGroup(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @Override
  public void clear(String group, String key) {
    String cacheKey = getCacheKey(group, key);
    Cache cache = getCache(group);
    cache.evict(cacheKey);
  }

  @Override
  public void clearAll() {
    List<String> groupNames = getGroupNames();
    for (String groupName : groupNames) {
      Cache cache = getCache(groupName);
      cache.clear();
    }
  }

  @Override
  public void clearGroup(String group) {
    Cache cache = getCache(group);
    cache.clear();
  }

  @Override
  public void clearKey(String key) {
    List<String> groupNames = getGroupNames();
    for (String groupName : groupNames) {
      Cache cache = getCache(groupName);
      String cacheKey = getCacheKey(groupName, key);
      cache.evict(cacheKey);
    }
  }

  @Override
  public Object get(String group, String key) {
    addGroup(group);
    Cache cache = getCache(group);
    String cacheKey = getCacheKey(group, key);
    ValueWrapper valueWrapper = cache.get(cacheKey);
    return getWrapperValue(valueWrapper);
  }

  @Override
  public <T> T get(String group, String key, Class<T> type) {
    addGroup(group);
    Cache cache = getCache(group);
    String cacheKey = getCacheKey(group, key);
    return cache.get(cacheKey, type);
  }

  @Override
  public <T> T get(String group, String key, Callable<T> valueLoader) {
    addGroup(group);
    Cache cache = getCache(group);
    String cacheKey = getCacheKey(group, key);
    return cache.get(cacheKey, valueLoader);
  }

  @Override
  public void put(String group, String key, Object value) {
    addGroup(group);
    String cacheKey = getCacheKey(group, key);
    Cache cache = getCache(group);
    cache.put(cacheKey, value);
  }

  @Override
  public <T> T putIfAbsent(String group, String key, T value) {
    addGroup(group);
    String cacheKey = getCacheKey(group, key);
    Cache cache = getCache(group);
    ValueWrapper valueWrapper = cache.putIfAbsent(cacheKey, value);
    return getWrapperValue(valueWrapper);
  }

  @Override
  public void setCacheName(String cacheName) {
    this.cacheName = cacheName;
  }

  /**
   * 记录缓存类别
   *
   * @param group 组
   */
  private void addGroup(String group) {
    List<String> groups = getGroupNames();
    if (!groups.contains(group)) {
      groups.add(group);
      Cache cache = getCache(GROUPS);
      cache.put(GROUPS, groups);
    }
  }

  /**
   * 获取缓存
   *
   * @param group 组名称
   * @return 缓存
   */
  private Cache getCache(String group) {
    String name = StringUtils.arrayToDelimitedString(new String[]{cacheName, group}, ".");
    return cacheManager.getCache(name);
  }

  /**
   * 生成缓存键
   *
   * @param group 缓存类别
   * @param key 键
   * @return 缓存键
   */
  private String getCacheKey(String group, String key) {
    return key;
  }

  /**
   * 获取缓存类别
   */
  @SuppressWarnings("unchecked")
  private List<String> getGroupNames() {
    Cache cache = getCache(GROUPS);
    List<String> list = cache.get(GROUPS, List.class);
    if (CollectionUtils.isEmpty(list)) {
      return new ArrayList<>();
    }
    return list;
  }

  /**
   * 获取包装值
   *
   * @param valueWrapper 包装器
   * @param <T> 值类型
   * @return 值
   */
  private <T> T getWrapperValue(ValueWrapper valueWrapper) {
    if (Objects.isNull(valueWrapper)) {
      return null;
    }
    return TypeUtils.cast(valueWrapper.get());
  }
}
