package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.boot.translate.DefaultSpringTranslator;
import site.morn.translate.Translator;

/**
 * 翻译器自动化配置
 *
 * @author timely-rain
 * @version 1.0.0, 2018/10/15
 * @since 1.0
 */
@Configuration
@ConditionalOnClass({Translator.class})
@ConditionalOnProperty(prefix = "morn.translator", value = "enabled", havingValue = "true")
public class TranslatorAutoConfiguration {

  /**
   * 注册翻译器
   *
   * @return 翻译器
   */
  @Bean
  @ConditionalOnMissingBean
  public Translator translator(MessageSource messageSource, IdentifiedBeanCache beanCache) {
    return new DefaultSpringTranslator(messageSource, beanCache);
  }
}
