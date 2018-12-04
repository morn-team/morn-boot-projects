package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.boot.bean.IdentifiedBeanPostProcessor;
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

  /**
   * 注册标识实例缓存
   *
   * <p>提供实例缓存功能
   *
   * @return 标识实例缓存
   */
  @Bean
  @ConditionalOnMissingBean
  public IdentifiedBeanCache identifiedBeanCache() {
    return new SimpleIdentifiedBeanCache();
  }

  /**
   * 注册标识实例后置处理器
   *
   * <p>识别实例注解并缓存
   *
   * @param identifiedBeanCache 标识实例缓存
   * @return 标识实例后置处理器
   */
  @Bean
  @ConditionalOnMissingBean
  public IdentifiedBeanPostProcessor identifiedBeanPostProcessor(
      IdentifiedBeanCache identifiedBeanCache) {
    return new IdentifiedBeanPostProcessor(identifiedBeanCache);
  }
}
