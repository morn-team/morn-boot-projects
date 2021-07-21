package site.morn.boot.notify;

import static site.morn.constant.ApplicationConstants.Notifies.ANNOTATION_NOTIFY_META_INITIAL_ADAPTER_NAME;
import static site.morn.constant.ApplicationConstants.Notifies.NOTIFY_LOG_PROCESSOR_NAME;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.boot.notify.support.AnnotationNotifyMetaInitialAdapter;
import site.morn.boot.notify.support.NotifyMetaLogProcessor;
import site.morn.boot.notify.template.ResourceNotifyTemplateResolver;
import site.morn.boot.notify.template.TemplateNotifyMetaLogProcessor;
import site.morn.boot.template.TemplateProperties;
import site.morn.notify.NotifyDispatcher;
import site.morn.notify.annotation.NotifyType;
import site.morn.notify.support.SimpleNotifyDispatcher;
import site.morn.translate.Translator;

/**
 * 系统通知自动化配置
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/10
 */
@Configuration
@EnableConfigurationProperties(NotifyProperties.class)
public class NotifyConfiguration implements BeanConfigurer {

  /**
   * 注册自定义注解
   */
  @Override
  public void addBeanAnnotations(AnnotationFieldRegistry registry) {
    // 注册通知类型注解
    registry.add(NotifyType.class);
  }

  /**
   * 注册元数据初始化适配器
   */
  @Bean(ANNOTATION_NOTIFY_META_INITIAL_ADAPTER_NAME)
  @ConditionalOnMissingBean
  public AnnotationNotifyMetaInitialAdapter annotationNotifyMetaInitialAdapter() {
    return new AnnotationNotifyMetaInitialAdapter();
  }

  /**
   * 注册通知切面
   */
  @Bean
  @ConditionalOnMissingBean
  public NotifyAspect notifyAspect(NotifyDispatcher dispatcher) {
    return new NotifyAspect(dispatcher);
  }

  /**
   * 注册通知调度者
   */
  @Bean
  @ConditionalOnMissingBean
  public NotifyDispatcher notifyDispatcher() {
    return new SimpleNotifyDispatcher();
  }

  /**
   * 注册通知日志处理者
   */
  @Bean(NOTIFY_LOG_PROCESSOR_NAME)
  @ConditionalOnMissingBean
  public NotifyMetaLogProcessor notifyMetaLogProcessor() {
    return new NotifyMetaLogProcessor();
  }

  /**
   * 注册资源通知模板解析器
   */
  @Bean
  @ConditionalOnMissingBean
  public ResourceNotifyTemplateResolver resourceNotifyTemplateResolver(
      TemplateProperties templateProperties, Translator translator) {
    return new ResourceNotifyTemplateResolver(templateProperties, translator);
  }

  /**
   * 注册资源模板通知处理者
   */
  @Bean
  @ConditionalOnMissingBean
  public TemplateNotifyMetaLogProcessor templateNotifyMetaLogProcessor() {
    return new TemplateNotifyMetaLogProcessor();
  }
}
