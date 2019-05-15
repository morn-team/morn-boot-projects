package site.morn.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * REST消息
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/8
 */
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class SimpleRestMessage implements RestMessage {

  /**
   * 成功标识
   */
  private boolean success;

  /**
   * 状态码
   */
  private String code;

  /**
   * 消息级别
   *
   * @see Level 级别枚举
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
   * @return REST消息
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T extends RestMessage> T setLevel(Level level) {
    this.level = level.value;
    return (T) this;
  }
}
