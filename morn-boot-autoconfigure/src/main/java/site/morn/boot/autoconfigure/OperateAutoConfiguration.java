package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.BeanCache;
import site.morn.boot.log.DefaultOperationConverter;
import site.morn.boot.log.OperateAspect;
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
public class OperateAutoConfiguration {

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
    return new DefaultOperationConverter(translator);
  }
}
