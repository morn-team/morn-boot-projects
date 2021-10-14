package site.morn.boot.autoconfigure;

import java.util.Locale;
import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import site.morn.bean.BeanPool;
import site.morn.boot.translate.DefaultSpringTranslator;
import site.morn.boot.translate.TranslateInitializer;
import site.morn.boot.translate.TranslateProperties;
import site.morn.translate.Translator;

/**
 * 翻译器自动化配置
 *
 * @author timely-rain
 * @version 1.0.0, 2018/10/15
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(TranslateProperties.class)
@ConditionalOnClass({Translator.class, LocaleResolver.class})
@ConditionalOnProperty(prefix = "morn.translator", value = "enabled", havingValue = "true")
public class TranslatorAutoConfiguration {

  /**
   * 注册翻译器
   *
   * @return 翻译器
   */
  @Bean
  @ConditionalOnMissingBean
  public Translator translator(MessageSource messageSource, BeanPool beanPool) {
    return new DefaultSpringTranslator(messageSource, beanPool);
  }

  /**
   * 注册翻译初始化器
   *
   * @param translator 翻译
   * @return 翻译初始化器
   */
  @Bean
  @ConditionalOnMissingBean
  public TranslateInitializer translateInitializer(Translator translator) {
    return new TranslateInitializer(translator);
  }

  /**
   * 注册国际化语言解析器
   *
   * @param properties 国际化配置
   * @return 国际化语言解析器
   */
  @Bean
  @ConditionalOnMissingBean
  public LocaleResolver localeResolver(TranslateProperties properties) {
    String language = Optional.ofNullable(properties.getLanguage())
        .orElse(Locale.CHINESE.getLanguage()); // 缺省时使用中文
    String country = Optional.ofNullable(properties.getCountry()).orElse("");  // 缺省时不限国家
    Locale locale = new Locale(language, country);
    CookieLocaleResolver localeResolver = new CookieLocaleResolver(); // 当前语言使用Cookie存储
    localeResolver.setDefaultLocale(locale);
    return localeResolver;
  }
}
