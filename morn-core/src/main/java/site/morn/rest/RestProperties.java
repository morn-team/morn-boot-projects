package site.morn.rest;

import lombok.Getter;
import lombok.Setter;

/**
 * REST配置项
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/18
 */
@Getter
@Setter
public class RestProperties {

  /**
   * 前缀
   */
  private String prefix;

  /**
   * 消息后缀
   */
  private String messageSuffix;

  /**
   * 成功编码
   */
  private String successCode;

  /**
   * 失败编码
   */
  private String failureCode;
}
