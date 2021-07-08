package site.morn.log;

import site.morn.translate.Translator;

/**
 * 操作日志记录模式
 *
 * @author timely-rain
 * @since 1.2.1, 2020/5/19
 */
public class OperateModes {

  /**
   * 简易模式
   * <p>默认使用简易模式构建日志</p>
   */
  public static final String SIMPLE = "simple";

  /**
   * 国际化模式
   * <p>基于国际化组件构建日志</p>
   *
   * @see Translator
   */
  public static final String I18N = "i18n";

  /**
   * 详细记录
   * <p>表示日志的解析和构建需要自定义实现</p>
   */
  public static final String DETAIL = "detail";

  private OperateModes() {
  }
}
