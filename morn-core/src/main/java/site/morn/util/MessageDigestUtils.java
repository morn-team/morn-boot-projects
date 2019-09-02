package site.morn.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;
import site.morn.bean.BeanCaches;
import site.morn.digest.Decryption;
import site.morn.digest.DigestMatcher;
import site.morn.digest.Encryption;

/**
 * 加密工具类
 *
 * @author timely-rain
 * @since 2.1.0, 2019/8/30
 */
@UtilityClass
public class MessageDigestUtils {

  /**
   * 解密
   *
   * @param algorithm {@link site.morn.constant.DigestConstant.Algorithms} 算法
   * @param text 密文
   * @return 明文
   */
  public static String decrypt(String algorithm, String text) {
    Decryption encryption = BeanCaches.tagBean(Decryption.class, algorithm);
    Assert.notNull(encryption, String.format("尚未支持[%s]解密算法", algorithm));
    return encryption.decrypt(text);
  }

  /**
   * 加密
   *
   * @param algorithm {@link site.morn.constant.DigestConstant.Algorithms} 算法
   * @param text 明文
   * @return 密文
   */
  public static String encrypt(String algorithm, String text) {
    Encryption encryption = BeanCaches.tagBean(Encryption.class, algorithm);
    Assert.notNull(encryption, String.format("尚未支持[%s]加密算法", algorithm));
    return encryption.encrypt(text);
  }

  /**
   * 校验
   *
   * @param algorithm {@link site.morn.constant.DigestConstant.Algorithms} 算法
   * @param rawText 明文
   * @param encodedText 密文
   * @return 校验是否通过
   */
  public static boolean matches(String algorithm, CharSequence rawText, String encodedText) {
    DigestMatcher matcher = BeanCaches.tagBean(DigestMatcher.class, algorithm);
    Assert.notNull(matcher, String.format("尚未支持[%s]校验算法", algorithm));
    return matcher.matches(rawText, encodedText);
  }
}
