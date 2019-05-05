package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.boot.exception.ApplicationMessageConverter;
import site.morn.boot.exception.DefaultApplicationMessageConverter;
import site.morn.exception.ExceptionProperties;
import site.morn.rest.convert.RestConverter;
import site.morn.translate.TranslateConverter;
import site.morn.translate.Translator;

/**
 * 异常自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/21
 */
@Configuration
@ConditionalOnClass(CacheManager.class)
@ConditionalOnProperty(prefix = "morn.exception", value = "enabled", havingValue = "true")
public class ExceptionAutoConfiguration {

  /**
   * 注册异常配置项
   *
   * @return 异常配置项
   */
  @Bean
  @ConfigurationProperties("morn.exception")
  public ExceptionProperties exceptionProperties() {
    return new ExceptionProperties();
  }

  /**
   * 注册应用消息转换器
   *
   * @param translator 翻译器
   * @return 应用消息转换器
   */
  @Bean
  @ConditionalOnMissingBean
  public TranslateConverter translateChanger(Translator translator,
      ExceptionProperties exceptionProperties) {
    return new DefaultApplicationMessageConverter(translator, exceptionProperties);
  }

  /**
   * 注册应用消息转换器
   *
   * @return 应用消息转换器
   */
  @Bean
  @ConditionalOnMissingBean
  public RestConverter restConverter() {
    return new ApplicationMessageConverter();
  }
}
