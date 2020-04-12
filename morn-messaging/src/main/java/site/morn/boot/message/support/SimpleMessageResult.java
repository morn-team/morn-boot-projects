package site.morn.boot.message.support;

import lombok.Getter;
import lombok.Setter;
import site.morn.boot.message.MessageResult;

/**
 * 消息操作结果
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
@Getter
@Setter
public class SimpleMessageResult implements MessageResult {

  /**
   * 状态
   */
  private String status;

  /**
   * 原结果
   */
  private Object originalResult;

  @Override
  public String getStatus() {
    return this.status;
  }

  @Override
  public Object getOriginalResult() {
    return this.originalResult;
  }
}
