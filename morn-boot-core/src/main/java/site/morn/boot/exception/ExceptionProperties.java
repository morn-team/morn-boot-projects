package site.morn.boot.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 异常配置项
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/20
 */
@Getter
@Setter
@ConfigurationProperties("morn.exception")
public class ExceptionProperties {

  /**
   * 前缀
   */
  private String prefix = "error";

  /**
   * 消息后缀
   */
  private String messageSuffix = "message";

  /**
   * 解决方案后缀
   */
  private String solutionSuffix = "solution";
}
