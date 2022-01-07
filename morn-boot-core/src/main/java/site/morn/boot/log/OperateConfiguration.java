package site.morn.boot.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.bean.BeanPool;
import site.morn.log.OperateMode;

/**
 * 操作日志配置
 *
 * @author timely-rain
 * @see EnableOperateLog
 * @see site.morn.boot.autoconfigure.OperateAutoConfiguration
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
  public OperateAspect operateAspect(BeanPool beanPool, OperateProperties properties) {
    return new OperateAspect(beanPool, properties);
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
   * 注册基础操作日志适配器
   */
  @Bean
  @ConditionalOnMissingBean
  public SimpleOperationAdapter simpleOperationAdapter() {
    return new SimpleOperationAdapter();
  }
}
