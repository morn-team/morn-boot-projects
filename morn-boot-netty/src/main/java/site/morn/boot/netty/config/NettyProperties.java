package site.morn.boot.netty.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Netty配置项
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
 */
@Getter
@Setter
public class NettyProperties {

  /**
   * 默认端口
   */
  public static final int DEFAULT_PORT = 18008;

  /**
   * Automatic start.
   *
   * @apiNote 自动启动
   */
  private boolean autoStart = true;

  /**
   * Reconnection interval.
   *
   * @apiNote 重连间隔
   */
  private long connectDelay = 1L;
}
