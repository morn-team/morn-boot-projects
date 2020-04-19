package site.morn.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 应用消息
 *
 * @author timely-rain
 * @since 1.0.0, 2018/10/16
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ApplicationMessage {

  /**
   * 状态码
   */
  private String code;

  /**
   * 消息内容
   */
  private String message;

  /**
   * 解决方案
   */
  private String solution;

  /**
   * 创建应用异常
   *
   * @return 应用异常
   */
  public ApplicationException exception() {
    return new ApplicationException(this);
  }
}
