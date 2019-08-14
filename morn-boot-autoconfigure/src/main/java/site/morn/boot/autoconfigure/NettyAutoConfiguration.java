package site.morn.boot.autoconfigure;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.BeanAnnotationRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.boot.netty.NettyClient;
import site.morn.boot.netty.NettyServer;
import site.morn.boot.netty.adapter.HexMessageDecoderProducer;
import site.morn.boot.netty.adapter.HexMessageEncoderProducer;
import site.morn.boot.netty.annotation.Inbound;
import site.morn.boot.netty.annotation.Outbound;
import site.morn.boot.netty.annotation.Terminal;
import site.morn.boot.netty.cache.DefaultNettyCache;
import site.morn.boot.netty.cache.NettyCache;

/**
 * Netty自动化配置
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/6
 */
@Slf4j
@Configuration
@ConditionalOnClass({Channel.class, NettyClient.class, NettyServer.class})
@AutoConfigureAfter(CacheAutoConfiguration.class)
public class NettyAutoConfiguration implements BeanConfigurer {

  @Override
  public void addBeanAnnotations(BeanAnnotationRegistry registry) {
    registry.add(Terminal.class);
    registry.add(Inbound.class);
    registry.add(Outbound.class);
  }

  /**
   * 注册十六进制解码器
   */
  @Bean
  public HexMessageDecoderProducer hexMessageDecoderProducer() {
    return new HexMessageDecoderProducer();
  }

  /**
   * 注册十六进制编码器
   */
  @Bean
  public HexMessageEncoderProducer hexMessageEncoderProducer() {
    return new HexMessageEncoderProducer();
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
}
