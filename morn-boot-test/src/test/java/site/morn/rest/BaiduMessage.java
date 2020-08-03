package site.morn.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 百度REST消息
 */
@Getter
@Setter
@ToString
public class BaiduMessage {

  /**
   * 状态码
   */
  private String error;

  /**
   * 消息内容
   */
  private String msg;

  /**
   * 消息数据
   */
  private Object data;
}
