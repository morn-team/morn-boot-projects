package site.morn.boot.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import site.morn.rest.RestMessage;
import site.morn.rest.SerialMessage;
import site.morn.rest.annotation.RestResponse;

/**
 * REST配置项
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/18
 */
@Getter
@Setter
@ConfigurationProperties("morn.rest")
public class RestProperties {

  /**
   * 前缀
   */
  private String prefix = "rest";

  /**
   * 消息后缀
   */
  private String messageSuffix = "message";

  /**
   * 成功编码
   */
  private String successCode = "success";

  /**
   * 失败编码
   */
  private String failureCode = "failure";

  /**
   * 全局响应类型
   *
   * @see site.morn.rest.RestConverter
   */
  private Class<?> responseClass = RestMessage.class;

  /**
   * 强制序列
   * <p>全局使用{@link SerialMessage}作为响应消息，包括未使用{@link RestResponse}的REST方法</p>
   */
  private boolean forceSerial = false;
}
