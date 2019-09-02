package site.morn.boot.netty.config;

import io.netty.bootstrap.Bootstrap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.boot.netty.NettyClient;

/**
 * Netty客户端自动化配置
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/6
 */
@Configuration
@ConditionalOnClass(Bootstrap.class)
public class NettyClientConfiguration {


  /**
   * 注册客户端配置项
   *
   * @return 客户端配置项
   */
  @Bean
  @ConfigurationProperties("morn.netty.client")
  public NettyClientProperties nettyClientProperties() {
    return new NettyClientProperties();
  }

  /**
   * 注册客户端
   *
   * @return Netty客户端
   */
  @Bean
  @ConditionalOnMissingBean
  public NettyClient nettyClient(NettyClientProperties properties) {
    return new NettyClient(properties);
  }
}
