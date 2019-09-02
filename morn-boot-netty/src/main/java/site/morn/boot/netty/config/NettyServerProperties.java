package site.morn.boot.netty.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Netty服务端配置项
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
 */
@Getter
@Setter
public class NettyServerProperties extends NettyProperties {

  /**
   * Listening port.
   *
   * @apiNote 监听端口
   */
  private int port = DEFAULT_PORT;
}
