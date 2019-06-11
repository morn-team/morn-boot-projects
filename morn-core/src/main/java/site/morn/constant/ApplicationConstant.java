package site.morn.constant;

import lombok.experimental.UtilityClass;

/**
 * 应用常量
 *
 * @author timely-rain
 * @since 1.0.2, 2019/4/30
 */
@UtilityClass
public class ApplicationConstant {

  /**
   * 缓存常量
   */
  @UtilityClass
  public class Cache {

    /**
     * 默认实例缓存名称
     */
    public static final String BEAN_DEFAULT = "bean.default";

    /**
     * Netty缓存
     */
    public static final String NETTY = "netty.default";

    /**
     * Netty通道缓存
     */
    public static final String NETTY_CHANNEL = "netty.channel";

    /**
     * Netty通道编号缓存
     */
    public static final String NETTY_CHANNEL_ID = "netty.channel.id";

    /**
     * Netty通道标识缓存
     */
    public static final String NETTY_CHANNEL_IDENTIFY = "netty.channel.identify";

    /**
     * Netty分组缓存
     */
    public static final String NETTY_GROUP = "netty.group";
  }

  /**
   * 异常常量
   */
  @UtilityClass
  public class Errors {

    /**
     * 默认实例缓存名称
     */
    public static final String BEAN_DEFAULT = "bean.default";
  }
}
