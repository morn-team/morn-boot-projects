package site.morn.cache;

import lombok.experimental.UtilityClass;
import org.springframework.cache.CacheManager;
import site.morn.bean.BeanCaches;

/**
 * 缓存工具类
 *
 * @author timely-rain
 * @since 1.2.1, 2019/9/5
 */
@UtilityClass
public class CacheGroups {

  /**
   * 获取缓存组
   *
   * @param groupName 组名称
   * @return 缓存组
   */
  public static CacheGroup cacheGroup(String groupName) {
    CacheManager cacheManager = BeanCaches.freeBean(CacheManager.class);
    SimpleCacheGroup cacheGroup = new SimpleCacheGroup(cacheManager);
    cacheGroup.setCacheName(groupName);
    return cacheGroup;
  }
}
