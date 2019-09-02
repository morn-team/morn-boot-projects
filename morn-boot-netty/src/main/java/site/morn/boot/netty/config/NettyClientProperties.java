package site.morn.boot.netty.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Netty客户端配置项
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
 */
@Getter
@Setter
public class NettyClientProperties extends NettyProperties {

  /**
   * Server host.
   *
   * @apiNote 服务端地址
   */
  private String serverHost = "localhost";

  /**
   * Server port.
   *
   * @apiNote 服务端端口
   */
  private int serverPort = DEFAULT_PORT;
}
