package site.morn.boot.cipher;

import static site.morn.cipher.support.AlgorithmNameConstants.MD5;

import java.util.Objects;
import org.springframework.util.DigestUtils;
import site.morn.cipher.AlgorithmEncryption;
import site.morn.cipher.AlgorithmMatcher;
import site.morn.cipher.annotation.AlgorithmName;
import site.morn.cipher.support.SimpleAlgorithmHolder;

/**
 * MD5算法
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/24
 */
public class MD5Algorithms {

  private MD5Algorithms() {
  }

  /**
   * 加密
   *
   * @param text 明文
   * @return 密文
   */
  public static String encrypt(CharSequence text) {
    byte[] bytes = text.toString().getBytes();
    return DigestUtils.appendMd5DigestAsHex(bytes, new StringBuilder()).toString();
  }

  /**
   * MD5加密
   */
  @AlgorithmName(MD5)
  public static class MD5Encryption extends SimpleAlgorithmHolder implements AlgorithmEncryption {

    @Override
    public String encrypt(CharSequence text) {
      return MD5Algorithms.encrypt(text);
    }
  }

  /**
   * MD5校验
   */
  @AlgorithmName(MD5)
  public static class MD5Matcher extends SimpleAlgorithmHolder implements AlgorithmMatcher {

    @Override
    public boolean matches(CharSequence rawText, CharSequence encodedText) {
      String encrypt = encrypt(rawText);
      return Objects.equals(encrypt, encodedText.toString());
    }
  }
}
