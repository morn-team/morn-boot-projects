package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.boot.exception.DefaultExceptionProcessor;
import site.morn.boot.exception.ExceptionInterpreterListener;
import site.morn.boot.exception.SimpleExceptionInterpreterCache;
import site.morn.boot.exception.interpreter.BindExceptionInterpreter;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionInterpreterCache;
import site.morn.exception.ExceptionProcessor;

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

  @Bean
  @ConditionalOnProperty(prefix = "morn.exception.bind", value = "enabled", havingValue = "true")
  public ExceptionInterpreter exceptionInterpreter() {
    return new BindExceptionInterpreter();
  }

  /**
   * 注册异常解析器缓存
   *
   * @return 异常解析器缓存
   */
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean(CacheManager.class)
  public ExceptionInterpreterCache exceptionInterpreterCache() {
    return new SimpleExceptionInterpreterCache();
  }

  /**
   * 注册异常处理器
   *
   * @param exceptionInterpreterCache 异常解释器缓存
   * @return 异常处理器
   */
  @Bean
  @ConditionalOnMissingBean
  public ExceptionProcessor exceptionProcessor(
      ExceptionInterpreterCache exceptionInterpreterCache) {
    return new DefaultExceptionProcessor(exceptionInterpreterCache);
  }

  /**
   * 注册异常解析监听器
   *
   * @return 异常解析监听器
   */
  @Bean
  public ExceptionInterpreterListener exceptionInterpreterListener(
      ExceptionInterpreterCache exceptionInterpreterCache) {
    return new ExceptionInterpreterListener(exceptionInterpreterCache);
  }
}
