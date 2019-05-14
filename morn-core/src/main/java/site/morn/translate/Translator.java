package site.morn.translate;

import java.util.Locale;

/**
 * 翻译器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/14
 */
public interface Translator {

  /**
   * 翻译
   *
   * @param code 国际化编码
   * @param args 国际化参数
   * @return 国际化消息
   */
  String translate(String code, Object... args);

  /**
   * 翻译
   *
   * @param code 国际化编码
   * @param args 国际化参数
   * @param defaultMessage 默认消息
   * @return 国际化消息
   */
  String translate(String code, Object[] args, String defaultMessage);

  /**
   * 翻译
   *
   * @param locale 语言
   * @param code 国际化编码
   * @param args 国际化参数
   * @return 国际化消息
   */
  String translate(Locale locale, String code, Object... args);

  /**
   * 翻译，根据翻译载体生成国际化消息
   *
   * @param transfer 翻译载体
   * @return 国际化消息
   */
  default String translate(Transfer transfer) {
    return translate(transfer.getCode(), transfer.getArgs());
  }

  /**
   * 翻译，根据翻译载体生成国际化对象
   *
   * @param transfer 翻译载体
   * @param cls 国际化类
   * @param <T> 国际化类型
   * @return 国际化对象
   */
  <T> T translate(Transfer transfer, Class<T> cls);

  /**
   * 翻译消息，或使用默认值
   *
   * @param code 国际化编码
   * @param defaultExpression 默认消息表达式
   * @param args 国际化参数
   * @return 国际化消息
   */
  default String orDefault(String code, String defaultExpression, Object... args) {
    String defaultMessage = String.format(defaultExpression, args);
    return translate(code, args, defaultMessage);
  }
}
