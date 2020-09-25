package site.morn.boot.autoconfigure;

import static site.morn.boot.netty.config.NettyProperties.Coders.HEX_STRING;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.boot.netty.NettyClient;
import site.morn.boot.netty.NettyServer;
import site.morn.boot.netty.adapter.HexMessageInboundHandler;
import site.morn.boot.netty.annotation.NettyHandler;
import site.morn.boot.netty.annotation.NettyTerminal;
import site.morn.boot.netty.cache.DefaultNettyCache;
import site.morn.boot.netty.cache.NettyCache;
import site.morn.boot.netty.coder.HexMessageStringDecoderProducer;
import site.morn.boot.netty.coder.HexMessageStringEncoderProducer;
import site.morn.boot.netty.config.NettyProperties;

/**
 * Netty自动化配置
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/6
 */
@Slf4j
@Configuration
@ConditionalOnClass({Channel.class, NettyClient.class, NettyServer.class})
@AutoConfigureAfter(CacheAutoConfiguration.class)
@EnableConfigurationProperties(NettyProperties.class)
public class NettyAutoConfiguration implements BeanConfigurer {

  @Override
  public void addBeanAnnotations(AnnotationFieldRegistry registry) {
    registry.add(NettyTerminal.class);
    registry.add(NettyHandler.class);
  }

  /**
   * 注册十六进制解码器
   */
  @Bean
  @ConditionalOnProperty(prefix = "morn.netty.coders", value = "name", havingValue = HEX_STRING)
  public HexMessageStringDecoderProducer hexMessageStringDecoderProducer() {
    return new HexMessageStringDecoderProducer();
  }

  /**
   * 注册十六进制编码器
   */
  @Bean
  @ConditionalOnProperty(prefix = "morn.netty.coders", value = "name", havingValue = HEX_STRING)
  public HexMessageStringEncoderProducer hexMessageStringEncoderProducer() {
    return new HexMessageStringEncoderProducer();
  }

  /**
   * 注册十六进制消息日志
   */
  @Bean
  public HexMessageInboundHandler hexMessageInboundHandler() {
    return new HexMessageInboundHandler();
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
