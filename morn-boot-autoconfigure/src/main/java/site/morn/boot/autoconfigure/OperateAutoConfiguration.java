package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.boot.log.I18nOperationConverter;
import site.morn.boot.log.OperateAspect;
import site.morn.boot.log.OperateProperties;
import site.morn.translate.Translator;

/**
 * 操作日志自动化配置
 *
 * @author timely-rain
 * @see site.morn.boot.log.OperateConfiguration
 * @since 1.2.2, 2021/7/8
 */
@Configuration
@ConditionalOnBean(OperateAspect.class)
@AutoConfigureAfter(TransactionAutoConfiguration.class)
public class OperateAutoConfiguration {

  /**
   * 注册日志转换器-国际化模式
   */
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean(Translator.class)
  public I18nOperationConverter i18nOperationConverter(OperateProperties properties,
      Translator translator) {
    return new I18nOperationConverter(properties, translator);
  }
}
