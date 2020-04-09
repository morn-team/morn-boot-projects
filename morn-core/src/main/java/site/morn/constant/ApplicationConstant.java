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
     * 主缓存管理器
     */
    public static final String CACHE_MANAGER_NAME_PRIMARY = "cacheManager";

    /**
     * Map缓存管理器
     */
    public static final String CACHE_MANAGER_NAME_SIMPLE = "simpleCacheManager";

    /**
     * 默认实例缓存
     */
    public static final String BEAN_DEFAULT = "bean.default";

    /**
     * 实例持有者缓存
     */
    public static final String BEAN_HOLDER = "bean.holder";

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

    /**
     * 任务-执行失败
     */
    public static final String TASK_EXECUTE_FAILURE = "task.execute-failure";

    /**
     * 任务-中断
     */
    public static final String TASK_INTERRUPTED = "task.interrupted";

    /**
     * 任务-尚未完成
     */
    public static final String TASK_NOT_DONE = "task.not-done";
  }
}
