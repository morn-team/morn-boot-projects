package site.morn.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;

/**
 * 默认缓存配置
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/7
 */
public class DefaultCachingConfigurer extends CachingConfigurerSupport {

  private final CacheManager cacheManager;

  public DefaultCachingConfigurer(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @Override
  public CacheManager cacheManager() {
    return cacheManager;
  }
}
