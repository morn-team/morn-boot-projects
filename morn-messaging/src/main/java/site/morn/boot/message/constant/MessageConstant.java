package site.morn.boot.message.constant;

import java.util.Objects;

/**
 * 消息常量
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
public class MessageConstant {

  private MessageConstant() {
  }

  /**
   * 异常码
   */
  public static class Errors {

    /**
     * 消息解析失败
     */
    public static final String MESSAGE_RESOLVE_FAILURE = "message.resolve-failure";

    /**
     * 消息-发送失败
     */
    public static final String MESSAGE_SEND_FAILURE = "message.send-failure";

    private Errors() {
    }
  }

  /**
   * 消息操作状态
   */
  public static class MessageResultStatus {

    /**
     * 成功
     */
    public static final String SUCCESS = "success";

    /**
     * 未知
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 丢失
     */
    public static final String MISS = "miss";

    /**
     * 失败
     */
    public static final String FAILURE = "failure";

    private MessageResultStatus() {
    }

    /**
     * 判断是否成功
     *
     * @param status 消息操作状态
     * @return 是否成功
     */
    public static boolean isSuccess(String status) {
      return Objects.equals(SUCCESS, status);
    }
  }
}