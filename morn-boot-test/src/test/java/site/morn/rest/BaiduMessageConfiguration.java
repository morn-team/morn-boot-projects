package site.morn.rest;

import org.springframework.context.annotation.Configuration;

/**
 * 百度消息配置
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/31
 */
@Configuration
public class BaiduMessageConfiguration implements RestConfigurer {

  @Override
  public void addSerialMessageClass(SerialMessageRegistry registry) {
    registry.register(BaiduMessage.class);
  }
}
