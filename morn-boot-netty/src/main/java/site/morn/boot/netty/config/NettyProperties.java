package site.morn.boot.netty.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Netty通用配置项
 *
 * @author timely-rain
 * @since 1.2.2, 2020/9/15
 */
@Getter
@Setter
@ConfigurationProperties("morn.netty")
public class NettyProperties {

  private final Coders coders = new Coders();

  /**
   * 编码器配置项
   */
  @Getter
  @Setter
  public static class Coders {

    public static final String HEX_BYTES = "hexBytes";

    public static final String HEX_STRING = "hexString";

    private String name = HEX_STRING;
  }
}
