package site.morn.translate;

/**
 * 翻译器工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/10
 */
public class Translators {

  /**
   * 默认翻译器
   */
  private static Translator defaultTranslator;

  private Translators() {
  }

  /**
   * 初始化
   *
   * @param translator 翻译器
   */
  public static void initialize(Translator translator) {
    Translators.defaultTranslator = translator;
  }

  /**
   * 获取默认翻译器
   *
   * @return 默认翻译器
   */
  public static Translator defaultTranslator() {
    return defaultTranslator;
  }

  /**
   * 格式化国际编码
   *
   * @param prefix 前缀
   * @param code 国际化编码
   * @param suffix 后缀
   * @return 国际化编码
   */
  public static String formatCode(String prefix, String code, String suffix) {
    return String.format("%s.%s.%s", prefix, code, suffix);
  }
}
