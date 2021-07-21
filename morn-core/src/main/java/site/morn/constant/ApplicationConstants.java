package site.morn.constant;

import lombok.experimental.UtilityClass;

/**
 * 应用常量
 *
 * @author timely-rain
 * @since 1.0.2, 2019/4/30
 */
@UtilityClass
public class ApplicationConstants {

  /**
   * 内建参数标识
   */
  public static final String BUILTIN = "_builtin";

  /**
   * 缓存常量
   */
  public static class Caches {

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

    private Caches() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * 异常常量
   */
  public static class Errors {

    /**
     * 默认实例缓存名称
     */
    public static final String BEAN_DEFAULT = "bean.default";

    /**
     * JSON反序列化失败
     */
    public static final String JSON_DESERIALIZE_FAILURE = "json.deserialize-failure";

    /**
     * JSON序列化失败
     */
    public static final String JSON_SERIALIZE_FAILURE = "json.serialize-failure";

    /**
     * 解密失败
     */
    public static final String DIGEST_DECRYPT_FAILURE = "digest.decrypt-failure";

    /**
     * 加密失败
     */
    public static final String DIGEST_ENCRYPT_FAILURE = "digest.encrypt-failure";

    /**
     * 密钥创建失败
     */
    public static final String DIGEST_SECRET_KEY_FAILURE = "digest.secret-key-failure";

    /**
     * 任务-尚未注册发布者
     */
    public static final String TASK_NO_PUBLISHER = "task.no-publisher";

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

    private Errors() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * 系统通知
   */
  public static class Notifies {

    /**
     * 注解式通知元数据初始化适配器
     */
    public static final String ANNOTATION_NOTIFY_META_INITIAL_ADAPTER_NAME = "annotationNotifyMetaInitialAdapter";

    /**
     * 通知日志处理者
     */
    public static final String NOTIFY_LOG_PROCESSOR_NAME = "notifyMetaLogProcessor";

    /**
     * 内置参数 - 通知注解
     */
    public static final String BUILTIN_NOTIFY_ANNOTATION = "_notifyAnnotation";

    /**
     * 内置参数 - 通知标识
     */
    public static final String BUILTIN_NOTIFY_KEY = "_notifyKey";

    /**
     * 内置参数 - 通知接收人标识
     */
    public static final String NOTIFY_RECEIVER_VALUE = "_notifyReceiverValue";

    private Notifies() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * 模板类型
   */
  public static class TemplateTypes {

    /**
     * 资源模板名称
     */
    public static final String RESOURCE = "resource";

    private TemplateTypes() {
      throw new UnsupportedOperationException();
    }
  }
}
