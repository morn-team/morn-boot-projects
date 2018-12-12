package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.boot.exception.DefaultApplicationMessageChanger;
import site.morn.boot.exception.DefaultExceptionProcessor;
import site.morn.boot.exception.interpreter.ApplicationExceptionInterpreter;
import site.morn.boot.exception.interpreter.BindExceptionInterpreter;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionProcessor;
import site.morn.translate.TranslateChanger;
import site.morn.translate.Translator;

/**
 * 异常解析器自动化配置
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/20
 * @since 1.0
 */
@Configuration
@ConditionalOnClass({ExceptionInterpreter.class, CacheManager.class})
@ConditionalOnProperty(prefix = "morn.exception", value = "enabled", havingValue = "true")
public class ExceptionInterpreterAutoConfiguration {

  /**
   * 注册异常处理器
   *
   * @param identifiedBeanCache 异常解释器缓存
   * @return 异常处理器
   */
  @Bean
  @ConditionalOnMissingBean
  public ExceptionProcessor exceptionProcessor(
      IdentifiedBeanCache identifiedBeanCache) {
    return new DefaultExceptionProcessor(identifiedBeanCache);
  }

  /**
   * 注册异常消息转换器
   *
   * @param translator 翻译器
   * @return 异常消息转换器
   */
  @Bean
  @ConditionalOnMissingBean
  public TranslateChanger translateChanger(Translator translator) {
    return new DefaultApplicationMessageChanger(translator);
  }

  /**
   * 注册应用异常解释器
   *
   * @return 应用异常解释器
   */
  @Bean
  @ConditionalOnMissingBean
  public ExceptionInterpreter applicationExceptionInterpreter() {
    return new ApplicationExceptionInterpreter();
  }

  /**
   * 注册数据绑定异常解释器
   *
   * @return 数据绑定异常解释器
   */
  @Bean
  @ConditionalOnProperty(prefix = "morn.exception.bind", value = "enabled", havingValue = "true")
  public ExceptionInterpreter bindExceptionInterpreter() {
    return new BindExceptionInterpreter();
  }
}
