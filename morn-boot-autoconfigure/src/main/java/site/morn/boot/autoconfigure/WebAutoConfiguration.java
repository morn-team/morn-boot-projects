package site.morn.boot.autoconfigure;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;
import javax.servlet.Servlet;
import org.aspectj.lang.Aspects;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.morn.boot.web.ExceptionHandlerAspect;
import site.morn.boot.web.WebExceptionResolver;
import site.morn.boot.web.WebMvcExceptionResolver;
import site.morn.boot.web.config.WebProperties;
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
   * 异常处理配置
   */
  @Configuration
  @ConditionalOnBean(ExceptionProcessor.class)
  @ConditionalOnClass({Aspects.class, ExceptionHandlerAspect.class,})
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

    /**
     * 注册Web异常解释器
     */
    @Bean
    @ConditionalOnMissingBean
    public WebExceptionResolver webExceptionResolver() {
      return new WebMvcExceptionResolver();
    }
  }

  /**
   * Web常规配置
   */
  @Configuration
  @ConditionalOnClass(Hibernate5Module.class)
  @EnableConfigurationProperties(WebProperties.class)
  public static class WebConfiguration {

    /**
     * 注册Hibernate5Module模块
     *
     * @see JacksonAutoConfiguration
     * @see Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    @ConditionalOnMissingBean
    public Hibernate5Module hibernate5Module() {
      Hibernate5Module hibernate5Module = new Hibernate5Module();
      hibernate5Module.disable(Feature.USE_TRANSIENT_ANNOTATION);
      return hibernate5Module;
    }
  }
}
