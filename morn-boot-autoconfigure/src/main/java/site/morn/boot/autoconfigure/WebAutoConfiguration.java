package site.morn.boot.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import javax.servlet.Servlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.morn.boot.web.ExceptionHandlerAspect;
import site.morn.exception.ExceptionProcessor;

/**
 * Web自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/12
 */
@Configuration
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
public class WebAutoConfiguration {

  /**
   * 注册全局异常处理切面
   *
   * @return 全局异常处理切面
   */
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean(ExceptionProcessor.class)
  @ConditionalOnProperty(prefix = "morn.exception-aspect", value = "enabled", havingValue = "true")
  public ExceptionHandlerAspect exceptionHandlerAspect(ExceptionProcessor exceptionProcessor) {
    return new ExceptionHandlerAspect(exceptionProcessor);
  }

  /**
   * 异常处理配置
   */
  @Configuration
  @ConditionalOnBean(ExceptionProcessor.class)
  @ConditionalOnClass(ExceptionHandlerAspect.class)
  public static class ExceptionHandlerConfiguration {

    /**
     * 注册全局异常处理切面
     *
     * @return 全局异常处理切面
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "morn.exception-aspect", value = "enabled", havingValue = "true")
    public ExceptionHandlerAspect exceptionHandlerAspect(ExceptionProcessor exceptionProcessor) {
      return new ExceptionHandlerAspect(exceptionProcessor);
    }
  }

  /**
   * JacksonHibernate模块配置
   */
  @Configuration
  @ConditionalOnClass(Hibernate5Module.class)
  public static class JacksonHibernateModuleConfiguration {

    /**
     * 注册Jackson消息转换器
     *
     * @return Jackson消息转换器
     */
    @Bean
    @ConditionalOnMissingBean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
      MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
      ObjectMapper objectMapper = messageConverter.getObjectMapper();
      objectMapper.registerModule(new Hibernate5Module());
      return messageConverter;
    }
  }
}
