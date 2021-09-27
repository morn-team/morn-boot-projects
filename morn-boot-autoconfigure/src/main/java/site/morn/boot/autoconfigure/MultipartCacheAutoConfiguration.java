package site.morn.boot.autoconfigure;

import static site.morn.constant.ApplicationConstants.Caches.CACHE_MANAGER_NAME_PRIMARY;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.cache.DefaultCachingConfigurer;

/**
 * 默认缓存自动化配置
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/7
 */
@Configuration
@ConditionalOnClass(CacheManager.class)
@ConditionalOnBean(CacheProperties.class)
public class MultipartCacheAutoConfiguration {

  private final CacheProperties cacheProperties;

  private final CacheManagerCustomizers customizerInvoker;

  public MultipartCacheAutoConfiguration(CacheProperties cacheProperties,
      CacheManagerCustomizers customizerInvoker) {
    this.cacheProperties = cacheProperties;
    this.customizerInvoker = customizerInvoker;
  }

  /**
   * 注册Map缓存管理器
   *
   * <p>常规类或者Bean无法序列化，只能放在Map缓存中
   */
  @Bean
  public ConcurrentMapCacheManager simpleCacheManager() {
    ConcurrentMapCacheManager mapCacheManager = new ConcurrentMapCacheManager();
    List<String> cacheNames = MultipartCacheAutoConfiguration.this.cacheProperties
        .getCacheNames();
    if (!cacheNames.isEmpty()) {
      mapCacheManager.setCacheNames(cacheNames);
    }
    return MultipartCacheAutoConfiguration.this.customizerInvoker.customize(mapCacheManager);
  }

  @Bean
  public CachingConfigurer cachingConfigurer(
      @Qualifier(CACHE_MANAGER_NAME_PRIMARY) CacheManager cacheManager) {
    return new DefaultCachingConfigurer(cacheManager);
  }

}
