package site.morn.boot.autoconfigure;

import javax.servlet.Servlet;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.morn.boot.log.I18nOperationConverter;
import site.morn.boot.log.OperateAspect;
import site.morn.boot.log.OperateProperties;
import site.morn.boot.security.SecurityOperationAdapter;
import site.morn.boot.web.config.WebProperties;
import site.morn.boot.web.log.WebOperationAdapter;
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

  /**
   * Web操作日志相关配置
   */
  @Configuration
  @ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class,
      WebProperties.class})
  public static class WebOperateConfiguration {

    /**
     * 注册Web操作日志适配器
     */
    @Bean
    @ConditionalOnMissingBean
    public WebOperationAdapter webOperationAdapter() {
      return new WebOperationAdapter();
    }
  }

  /**
   * Security操作日志相关配置
   */
  @Configuration
  @ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class,
      SecurityContext.class, SecurityOperationAdapter.class})
  public static class SecurityOperateConfiguration {

    /**
     * 注册Security操作日志适配器
     */
    @Bean
    @ConditionalOnMissingBean
    public SecurityOperationAdapter securityOperationAdapter() {
      return new SecurityOperationAdapter();
    }
  }
}
