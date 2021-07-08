package site.morn.boot.autoconfigure;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.boot.json.JsonParser;
import site.morn.boot.json.support.FastJsonParser;
import site.morn.boot.json.support.JacksonParser;

/**
 * JSON自动化配置
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/24
 */
@Configuration
@ConditionalOnClass(JsonParser.class)
public class JsonAutoConfiguration {

  /**
   * FastJson自动化配置
   */
  @Configuration
  @ConditionalOnClass(JSON.class)
  public static class FastJsonAutoConfiguration {

    /**
     * 注册FastJson映射器
     */
    @Bean
    @ConditionalOnMissingBean
    public FastJsonParser fastJsonMapper() {
      return new FastJsonParser();
    }
  }

  /**
   * Jackson自动化配置
   */
  @Configuration
  @ConditionalOnClass(ObjectMapper.class)
  public static class JacksonAutoConfiguration {

    /**
     * 注册FastJson映射器
     */
    @Bean
    @ConditionalOnMissingBean
    public JacksonParser jacksonParser(ObjectMapper objectMapper) {
      return new JacksonParser(objectMapper);
    }
  }
}
