package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.boot.bean.SimpleIdentifiedBeanCache;

/**
 * 实例自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/28
 */
@Configuration
@ConditionalOnClass(CacheManager.class)
public class BeanAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public IdentifiedBeanCache identifiedBeanCache() {
    return new SimpleIdentifiedBeanCache();
  }
}
