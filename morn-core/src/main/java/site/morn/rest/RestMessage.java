package site.morn.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * REST消息体
 *
 * @author timely-rain
 * @since 1.0.0, 2018/7/25
 */
@ApiModel(value = "REST消息", description = "统一消息模型，通常用于数据响应")
public interface RestMessage {

  /**
   * 成功标识
   */
  @ApiModelProperty("成功标识")
  boolean isSuccess();

  /**
   * 状态码
   */
  @ApiModelProperty("状态码")
  String getCode();

  /**
   * 消息级别
   *
   * @see Level 级别枚举
   */
  @ApiModelProperty("消息级别")
  String getLevel();

  /**
   * 消息内容
   */
  @ApiModelProperty("消息内容")
  String getMessage();

  /**
   * 数据
   */
  @ApiModelProperty("数据")
  <T> T getData();

  /**
   * 成功标识
   */
  <T extends RestMessage> T setSuccess(boolean value);

  /**
   * 状态码
   */
  <T extends RestMessage> T setCode(String value);

  /**
   * 消息级别
   *
   * @see Level 级别枚举
   */
  <T extends RestMessage> T setLevel(Level level);

  /**
   * 消息内容
   */
  <T extends RestMessage> T setMessage(String value);

  /**
   * 数据
   */
  <T extends RestMessage> T setData(Object value);

  /**
   * 消息级别
   *
   * @author timely-rain
   * @version 1.0.0, 2018/8/6
   * @since 1.0
   */
  enum Level {
    /**
     * 调试
     */
    DEBUG("debug"),
    /**
     * 信息
     */
    INFO("info"),
    /**
     * 警告
     */
    WARNING("warning"),
    /**
     * 错误
     */
    ERROR("error");

    /**
     * 值
     */
    String value;

    Level(String value) {
      this.value = value;
    }
  }
}
