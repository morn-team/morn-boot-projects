package site.morn.cache;

import static site.morn.constant.ApplicationConstant.Cache.CACHE_MANAGER_NAME_PRIMARY;

import lombok.experimental.UtilityClass;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import site.morn.bean.support.BeanCaches;

/**
 * 缓存工具类
 *
 * @author timely-rain
 * @since 1.2.1, 2019/9/5
 */
@UtilityClass
public class CacheGroups {

  /**
   * 获取缓存池
   *
   * @param name 缓存池名称
   * @return 缓存池
   */
  public static Cache cache(String name) {
    CacheManager cacheManager = BeanCaches.getBean(CACHE_MANAGER_NAME_PRIMARY, CacheManager.class);
    return cacheManager.getCache(name);
  }

  /**
   * 获取缓存组
   *
   * @param cacheName 缓存名称
   * @return 缓存组
   */
  public static CacheGroup cacheGroup(String cacheName) {
    CacheManager cacheManager = BeanCaches.getBean(CACHE_MANAGER_NAME_PRIMARY, CacheManager.class);
    SimpleCacheGroup cacheGroup = new SimpleCacheGroup(cacheManager);
    cacheGroup.setCacheName(cacheName);
    return cacheGroup;
  }
}
