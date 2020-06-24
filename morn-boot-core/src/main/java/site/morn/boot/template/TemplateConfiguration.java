package site.morn.boot.template;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.boot.template.support.ResourceTemplateResolver;
import site.morn.template.annotation.TemplateType;
import site.morn.translate.Translator;

/**
 * 模板自动化配置
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/12
 */
@Configuration
@EnableConfigurationProperties(TemplateProperties.class)
public class TemplateConfiguration implements BeanConfigurer {

  /**
   * 注册自定义注解
   */
  @Override
  public void addBeanAnnotations(AnnotationFieldRegistry registry) {
    // 注册模板类型注解
    registry.add(TemplateType.class);
  }

  /**
   * 注册资源模板解析器
   *
   * @param properties 模板配置
   * @param translator 国际化翻译器
   */
  @Bean
  @ConditionalOnMissingBean
  public ResourceTemplateResolver resourceTemplateResolver(TemplateProperties properties,
      Translator translator) {
    return new ResourceTemplateResolver(properties, translator);
  }
}
