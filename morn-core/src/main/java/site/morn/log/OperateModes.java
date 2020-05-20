package site.morn.log;

/**
 * 操作日志记录模式
 *
 * @author timely-rain
 * @since 1.2.1, 2020/5/19
 */
public class OperateModes {

  /**
   * 简易记录
   * <p>默认使用简易记录，并且内置的日志切面也是基于简易记录构建日志的</p>
   */
  public static final String SIMPLE = "simple";

  /**
   * 详细记录
   * <p>表示日志的解析和构建需要自定义实现</p>
   */
  public static final String DETAIL = "detail";

  private OperateModes() {
  }
}
