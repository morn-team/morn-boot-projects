package site.morn.boot.netty.constant;

import lombok.experimental.UtilityClass;

/**
 * 终端类型
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/5
 */
@UtilityClass
public class TerminalTypeConstants {

  /**
   * 既适用客户端，也适用服务端
   */
  public static final String BOTH = "*";

  /**
   * 客户端
   */
  public static final String CLIENT = "client";

  /**
   * 服务端
   */
  public static final String SERVER = "server";
}
