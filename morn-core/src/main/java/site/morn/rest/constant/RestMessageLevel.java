package site.morn.rest.constant;

/**
 * 消息级别
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/6
 */
public enum RestMessageLevel {
  /**
   * 调试
   */
  DEBUG("debug"),
  /**
   * 信息
   */
  INFO("info"),
  /**
   * 警告
   */
  WARNING("warning"),
  /**
   * 错误
   */
  ERROR("error");

  /**
   * 值
   */
  private String value;

  RestMessageLevel(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
