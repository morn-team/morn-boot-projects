package site.morn.boot.netty.config;

import io.netty.bootstrap.ServerBootstrap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.boot.netty.NettyServer;
import site.morn.boot.netty.adapter.NettyCacheAdapter;
import site.morn.boot.netty.cache.DefaultNettyCache;
import site.morn.boot.netty.cache.NettyCache;

/**
 * Netty服务端自动化配置
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/15
 */
@Configuration
@ConditionalOnClass(ServerBootstrap.class)
public class NettyServerAutoConfiguration {

  /**
   * 注册服务端配置项
   *
   * @return 服务端配置项
   */
  @Bean
  @ConfigurationProperties("morn.netty.server")
  public NettyServerProperties nettyServerProperties() {
    return new NettyServerProperties();
  }

  /**
   * 注册服务端
   *
   * @return Netty服务端
   */
  @Bean
  @ConditionalOnMissingBean
  public NettyServer nettyServer(NettyServerProperties properties) {
    return new NettyServer(properties);
  }

  /**
   * 注册缓存
   *
   * @return 缓存
   */
  @Bean
  @ConditionalOnMissingBean
  public NettyCache nettyCache() {
    return new DefaultNettyCache();
  }

  /**
   * 注册缓存适配器
   *
   * @return 缓存适配器
   */
  @Bean
  @ConditionalOnMissingBean
  public NettyCacheAdapter nettyCacheAdapter() {
    return new NettyCacheAdapter();
  }
}
