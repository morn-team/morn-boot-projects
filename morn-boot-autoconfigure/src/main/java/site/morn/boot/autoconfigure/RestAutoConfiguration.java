package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.boot.rest.RestInitializer;
import site.morn.rest.RestProperties;
import site.morn.translate.Translator;

/**
 * REST自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/29
 */
@Configuration
@ConditionalOnClass(CacheManager.class)
public class RestAutoConfiguration {

  /**
   * 注册REST配置项
   *
   * @return REST配置项
   */
  @Bean
  @ConfigurationProperties("morn.rest")
  public RestProperties restProperties() {
    return new RestProperties();
  }

  /**
   * 注册REST初始化器
   *
   * @param identifiedBeanCache 实例缓存
   * @param translator 翻译器
   * @return REST初始化器
   */
  @Bean
  public RestInitializer restInitializer(IdentifiedBeanCache identifiedBeanCache,
      Translator translator, RestProperties restProperties) {
    return new RestInitializer(identifiedBeanCache, translator, restProperties);
  }
}
