package site.morn.rest.support;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.morn.rest.RestMessage;
import site.morn.rest.constant.RestMessageLevel;

/**
 * REST消息
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/8
 */
@Getter
@Setter
@ToString
public class SimpleRestMessage implements RestMessage {

  /**
   * 成功标识
   */
  private int status;

  /**
   * 状态码
   */
  private String code;

  /**
   * 消息级别
   *
   * @see RestMessageLevel 级别枚举
   */
  private String level;

  /**
   * 消息内容
   */
  private String message;

  /**
   * 数据
   */
  private Object data;

  /**
   * 设置消息级别
   *
   * @param level 消息级别枚举
   */
  @Override
  public void setLevel(RestMessageLevel level) {
    this.level = level.getValue();
  }
}
