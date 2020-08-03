package site.morn.boot.autoconfigure;

import org.apache.shiro.mgt.SecurityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.BeanCache;
import site.morn.boot.exception.DefaultExceptionInterpreterService;
import site.morn.boot.exception.interpreter.ApplicationExceptionInterpreter;
import site.morn.boot.exception.interpreter.MethodValidateExceptionInterpreter;
import site.morn.boot.exception.interpreter.ShiroExceptionInterpreter;
import site.morn.boot.exception.interpreter.ValidateExceptionInterpreter;
import site.morn.boot.exception.interpreter.ValidationExceptionInterpreter;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionInterpreterService;

/**
 * 异常解析器自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/20
 */
@Configuration
@ConditionalOnClass({ExceptionInterpreter.class, CacheManager.class})
@ConditionalOnProperty(prefix = "morn.exception-interpreter", value = "enabled", havingValue = "true")
public class ExceptionInterpreterAutoConfiguration {

  /**
   * 注册异常处理器
   *
   * @param beanCache 异常解释器缓存
   * @return 异常处理器
   */
  @Bean
  @ConditionalOnMissingBean
  public ExceptionInterpreterService exceptionProcessor(BeanCache beanCache) {
    return new DefaultExceptionInterpreterService(beanCache);
  }

  /**
   * 注册应用异常解释器
   *
   * @return 应用异常解释器
   */
  @Bean
  public ExceptionInterpreter applicationExceptionInterpreter() {
    return new ApplicationExceptionInterpreter();
  }

  /**
   * Shiro相关配置
   */
  @Configuration
  @ConditionalOnClass({SecurityManager.class, ShiroExceptionInterpreter.class})
  @ConditionalOnProperty(prefix = "morn.exception-interpreter.shiro", value = "enabled", havingValue = "true")
  public class ShiroConfiguration {

    /**
     * 注册Shiro异常解释器
     *
     * @return Shiro异常解释器
     */
    @Bean
    public ExceptionInterpreter shiroExceptionInterpreter() {
      return new ShiroExceptionInterpreter();
    }
  }

  @Configuration
  @ConditionalOnClass({ValidateExceptionInterpreter.class, ValidationExceptionInterpreter.class,
      MethodValidateExceptionInterpreter.class})
  @ConditionalOnProperty(prefix = "morn.exception-interpreter.validate", value = "enabled", havingValue = "true")
  public class ValidateConfiguration {

    /**
     * 注册数据校验异常解释器
     *
     * @return 数据校验异常解释器
     */
    @Bean
    public ExceptionInterpreter validateExceptionInterpreter() {
      return new ValidateExceptionInterpreter();
    }

    /**
     * 注册数据校验异常解释器
     *
     * @return 数据校验异常解释器
     */
    @Bean
    public ExceptionInterpreter validationExceptionInterpreter() {
      return new ValidationExceptionInterpreter();
    }

    /**
     * 注册数据校验异常解释器
     *
     * @return 数据校验异常解释器
     */
    @Bean
    public ExceptionInterpreter methodValidateExceptionInterpreter() {
      return new MethodValidateExceptionInterpreter();
    }
  }
}
