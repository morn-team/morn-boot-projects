package site.morn.boot.digest;

import static site.morn.constant.DigestConstant.Algorithms.MD5;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import site.morn.digest.DigestMatcher;
import site.morn.digest.Encryption;
import site.morn.digest.annotation.DigestAlgorithm;

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
   * MD5加密算法
   *
   * @author timely-rain
   * @since 1.2.1, 2020/4/24
   */
  @DigestAlgorithm(MD5)
  public static class MD5Encryption implements Encryption {

    @Override
    public String encrypt(CharSequence text) {
      return MD5Algorithms.encrypt(text);
    }
  }

  /**
   * MD5校验算法
   *
   * @author timely-rain
   * @since 1.2.1, 2020/4/24
   */
  @Slf4j
  @DigestAlgorithm(MD5)
  public static class MD5Matcher implements DigestMatcher {

    @Override
    public boolean matches(CharSequence rawText, String encodedText) {
      String encrypt = encrypt(rawText);
      return Objects.equals(encrypt, encodedText);
    }
  }
}
