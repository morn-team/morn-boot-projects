package site.morn.digest;

/**
 * 数据加密校验
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
public interface DigestMatcher {

  /**
   * 匹配校验
   *
   * @param rawText 明文
   * @param encodedText 密文
   * @return 校验是否通过
   */
  boolean matches(CharSequence rawText, String encodedText);
}
