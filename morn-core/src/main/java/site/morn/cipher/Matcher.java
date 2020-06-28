package site.morn.cipher;

/**
 * 校验者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
@FunctionalInterface
public interface Matcher {

  /**
   * 匹配校验
   *
   * @param rawText 明文
   * @param encodedText 密文
   * @return 校验是否通过
   */
  boolean matches(CharSequence rawText, CharSequence encodedText);
}
