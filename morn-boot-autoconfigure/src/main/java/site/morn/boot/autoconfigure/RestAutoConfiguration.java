package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.BeanPool;
import site.morn.boot.rest.RestInitializer;
import site.morn.boot.rest.RestProperties;
import site.morn.rest.RestConverterService;
import site.morn.rest.support.SimpleRestConverterService;
import site.morn.translate.Translator;

/**
 * REST自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/29
 */
@Configuration
@ConditionalOnClass(CacheManager.class)
@EnableConfigurationProperties(RestProperties.class)
@AutoConfigureAfter(TranslatorAutoConfiguration.class)
public class RestAutoConfiguration {

  /**
   * 注册REST消息转换器服务
   */
  @Bean
  @ConditionalOnMissingBean
  public RestConverterService restConverterService() {
    return new SimpleRestConverterService();
  }

  /**
   * 注册REST初始化器
   *
   * @param beanPool   实例缓存
   * @param translator 翻译器
   * @return REST初始化器
   */
  @Bean
  @ConditionalOnBean(Translator.class)
  public RestInitializer restInitializer(BeanPool beanPool, Translator translator,
      RestProperties restProperties) {
    return new RestInitializer(beanPool, translator, restProperties);
  }
}
