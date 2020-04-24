package site.morn.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import site.morn.bean.support.BeanCaches;
import site.morn.bean.support.Tags;
import site.morn.digest.Decryption;
import site.morn.digest.DigestMatcher;
import site.morn.digest.Encryption;
import site.morn.digest.annotation.DigestAlgorithm;

/**
 * 加密工具类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
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
  public String decrypt(String algorithm, String text) {
    if (StringUtils.isEmpty(text)) {
      return text;
    }
    String[] tags = Tags.from(DigestAlgorithm.class, algorithm).toArray();
    Decryption encryption = BeanCaches.tagBean(Decryption.class, tags);
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
  public String encrypt(String algorithm, String text) {
    if (StringUtils.isEmpty(text)) {
      return text;
    }
    String[] tags = Tags.from(DigestAlgorithm.class, algorithm).toArray();
    Encryption encryption = BeanCaches.tagBean(Encryption.class, tags);
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
  public boolean matches(String algorithm, CharSequence rawText, String encodedText) {
    String[] tags = Tags.from(DigestAlgorithm.class, algorithm).toArray();
    DigestMatcher matcher = BeanCaches.tagBean(DigestMatcher.class, tags);
    Assert.notNull(matcher, String.format("尚未支持[%s]校验算法", algorithm));
    return matcher.matches(rawText, encodedText);
  }
}
