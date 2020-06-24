package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.BeanCache;
import site.morn.bean.BeanConfigurer;
import site.morn.boot.log.OperateAspect;
import site.morn.boot.log.SimpleOperationConverter;
import site.morn.log.OperateMode;
import site.morn.log.OperationConverter;
import site.morn.translate.Translator;

/**
 * 操作日志自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Configuration
@ConditionalOnProperty(prefix = "morn.operate", value = "enabled", havingValue = "true")
public class OperateAutoConfiguration implements BeanConfigurer {

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
  public OperateAspect operateAspect(BeanCache beanCache) {
    return new OperateAspect(beanCache);
  }

  @Bean
  @ConditionalOnMissingBean
  public OperationConverter operationConverter(Translator translator) {
    return new SimpleOperationConverter(translator);
  }
}
