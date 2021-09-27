package site.morn.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import site.morn.rest.constant.RestMessageConstants;
import site.morn.rest.constant.RestMessageLevel;

/**
 * REST消息体
 *
 * @author timely-rain
 * @since 1.0.0, 2018/7/25
 */
@ApiModel(value = "REST消息", description = "统一消息模型，通常用于数据响应")
public interface RestMessage extends SerialMessage {

  /**
   * 状态码
   *
   * @see RestMessageConstants
   */
  @ApiModelProperty("状态码")
  int getStatus();

  /**
   * 错误码
   */
  @ApiModelProperty("错误码")
  String getCode();

  /**
   * 消息级别
   *
   * @see RestMessageLevel 级别枚举
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
   * 状态码
   */
  void setStatus(int value);

  /**
   * 错误码
   */
  void setCode(String value);

  /**
   * 消息级别
   *
   * @see RestMessageLevel 级别枚举
   */
  void setLevel(RestMessageLevel level);

  /**
   * 消息内容
   */
  void setMessage(String value);

  /**
   * 数据
   */
  void setData(Object value);
}
