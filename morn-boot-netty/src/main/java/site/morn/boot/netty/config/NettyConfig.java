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
public class NettyConfig {

  /**
   * 默认地址
   */
  public static final String DEFAULT_HOST = "localhost";

  /**
   * 默认端口
   */
  public static final int DEFAULT_PORT = 18008;
}
