package site.morn.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 异常配置项
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/20
 */
@Getter
@Setter
public class ExceptionProperties {

  /**
   * 前缀
   */
  private String prefix;

  /**
   * 默认编码
   */
  private String defaultCode;

  /**
   * 消息后缀
   */
  private String messageSuffix;

  /**
   * 解决方案后缀
   */
  private String solutionSuffix;
}
