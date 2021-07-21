package site.morn.boot.netty.config;

import static site.morn.boot.netty.config.NettyConfigConstants.DEFAULT_PORT;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Netty服务端配置项
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
 */
@Getter
@Setter
@ConfigurationProperties("morn.netty.server")
public class NettyServerProperties {

  /**
   * Listening port.
   *
   * @apiNote 监听端口
   */
  private int port = DEFAULT_PORT;

  /**
   * Automatic start.
   *
   * @apiNote 自动启动
   */
  private boolean autoStart = true;
}
