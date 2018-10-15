package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.boot.exception.DefaultExceptionResolver;
import site.morn.boot.exception.ExceptionResolverListener;
import site.morn.boot.exception.SimpleExceptionResolverCache;
import site.timely.exception.ExceptionResolver;
import site.timely.exception.ExceptionResolverCache;

/**
 * 异常解析器自动化配置
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/20
 * @since 1.0
 */
@Configuration
@ConditionalOnClass({ExceptionResolver.class, CacheManager.class})
@ConditionalOnProperty(prefix = "morn.exception", value = "enabled", havingValue = "true")
public class ExceptionResolverAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public ExceptionResolver exceptionResolver() {
    return new DefaultExceptionResolver();
  }

  /**
   * 注册异常解析器缓存
   *
   * @return 异常解析器缓存
   */
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean(CacheManager.class)
  public ExceptionResolverCache exceptionResolverCache() {
    return new SimpleExceptionResolverCache();
  }

  /**
   * 注册异常解析监听器
   *
   * @return 异常解析监听器
   */
  @Bean
  public ExceptionResolverListener exceptionResolverListener(
      ExceptionResolverCache exceptionResolverCache) {
    return new ExceptionResolverListener(exceptionResolverCache);
  }
}
