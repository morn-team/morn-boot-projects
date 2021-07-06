package site.morn.boot.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.BeanCache;
import site.morn.bean.BeanConfigurer;
import site.morn.log.OperateMode;
import site.morn.translate.Translator;

/**
 * 操作日志自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Configuration
@EnableConfigurationProperties(OperateProperties.class)
public class OperateConfiguration implements BeanConfigurer {

  @Override
  public void addBeanAnnotations(AnnotationFieldRegistry registry) {
    registry.add(OperateMode.class);
  }

  /**
   * 注册操作日志切面
   *
   * @return 操作日志切面
   */
  @Bean
  @ConditionalOnMissingBean
  public OperateAspect operateAspect(BeanCache beanCache, OperateProperties properties) {
    return new OperateAspect(beanCache, properties);
  }

  /**
   * 注册日志转换器-简易模式
   */
  @Bean
  @ConditionalOnMissingBean
  public SimpleOperationConverter simpleOperationConverter(OperateProperties properties) {
    return new SimpleOperationConverter(properties);
  }

  /**
   * 注册日志转换器-国际化模式
   */
  @Bean
  @ConditionalOnMissingBean
  public I18nOperationConverter i18nOperationConverter(OperateProperties properties,
      Translator translator) {
    return new I18nOperationConverter(properties, translator);
  }
}
