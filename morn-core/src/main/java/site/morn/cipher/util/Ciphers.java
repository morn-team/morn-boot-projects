package site.morn.cipher.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import site.morn.bean.support.BeanCaches;
import site.morn.bean.support.Tags;
import site.morn.cipher.Algorithm;
import site.morn.cipher.AlgorithmDecryption;
import site.morn.cipher.AlgorithmEncryption;
import site.morn.cipher.AlgorithmMatcher;
import site.morn.cipher.annotation.AlgorithmName;
import site.morn.cipher.support.AlgorithmBuilder;
import site.morn.cipher.support.AlgorithmNames;
import site.morn.util.SpareArrayUtils;

/**
 * 加密工具类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
@UtilityClass
public class Ciphers {

  /**
   * 解密
   *
   * @param algorithmName {@link AlgorithmNames} 算法
   * @param text          密文
   * @return 明文
   */
  public String decrypt(String algorithmName, CharSequence text) {
    Algorithm algorithm = AlgorithmBuilder.withName(algorithmName).build();
    return decrypt(algorithm, text);
  }

  /**
   * 解密
   *
   * @param algorithm 解密算法
   * @param text      密文
   * @return 明文
   */
  public String decrypt(Algorithm algorithm, CharSequence text) {
    if (!StringUtils.hasText(text)) {
      return "";
    }
    String algorithmName = algorithm.getName();
    String[] tags = Tags.from(AlgorithmName.class, algorithmName).toArray();
    AlgorithmDecryption decryption = BeanCaches.tagBean(AlgorithmDecryption.class, tags);
    Assert.notNull(decryption, String.format("尚未支持[%s]解密算法", algorithmName));
    decryption.setAlgorithm(algorithm);
    return decryption.decrypt(text);
  }

  /**
   * 加密
   *
   * @param algorithmName {@link AlgorithmNames} 算法
   * @param text          明文
   * @return 密文
   */
  public String encrypt(String algorithmName, CharSequence text) {
    Algorithm algorithm = AlgorithmBuilder.withName(algorithmName).build();
    return encrypt(algorithm, text);
  }

  /**
   * 加密
   *
   * @param algorithm 加密算法
   * @param text      明文
   * @return 密文
   */
  public String encrypt(Algorithm algorithm, CharSequence text) {
    if (!StringUtils.hasText(text)) {
      return "";
    }
    String algorithmName = algorithm.getName();
    String[] tags = Tags.from(AlgorithmName.class, algorithmName).toArray();
    AlgorithmEncryption encryption = BeanCaches.tagBean(AlgorithmEncryption.class, tags);
    Assert.notNull(encryption, String.format("尚未支持[%s]加密算法", algorithmName));
    encryption.setAlgorithm(algorithm);
    return encryption.encrypt(text);
  }


  /**
   * 校验
   *
   * @param algorithmName {@link AlgorithmNames} 算法名称
   * @param rawText       明文
   * @param encodedText   密文
   * @return 校验是否通过
   */
  public boolean matches(String algorithmName, CharSequence rawText, CharSequence encodedText) {
    Algorithm algorithm = AlgorithmBuilder.withName(algorithmName).build();
    return matches(algorithm, rawText, encodedText);
  }

  /**
   * 校验
   *
   * @param algorithm   {@link AlgorithmNames} 算法
   * @param rawText     明文
   * @param encodedText 密文
   * @return 校验是否通过
   */
  public boolean matches(Algorithm algorithm, CharSequence rawText, CharSequence encodedText) {
    String algorithmName = algorithm.getName();
    String[] tags = Tags.from(AlgorithmName.class, algorithmName).toArray();
    AlgorithmMatcher matcher = BeanCaches.tagBean(AlgorithmMatcher.class, tags);
    Assert.notNull(matcher, String.format("尚未支持[%s]校验算法", algorithmName));
    matcher.setAlgorithm(algorithm);
    return matcher.matches(rawText, encodedText);
  }

  /**
   * 文本转为字节
   *
   * @param text 文本
   * @return 字节数组
   */
  public byte[] parseBytes(CharSequence text) {
    Assert.isTrue(StringUtils.hasText(text), "文本不能为空");
    return text.toString().getBytes(StandardCharsets.UTF_8);
  }

  /**
   * 字节转为文本
   *
   * @param bytes 字节数组
   * @return 文本
   */
  public String parseString(byte[] bytes) {
    Assert.isTrue(!SpareArrayUtils.isEmpty(bytes), "字节不能为空");
    return new String(bytes, StandardCharsets.UTF_8);
  }

  /**
   * Base64文本转为字节
   *
   * @param text 字节数组
   * @return Base64文本
   */
  public byte[] decodeBase64(CharSequence text) {
    Assert.isTrue(StringUtils.hasText(text), "文本不能为空");
    return Base64.getDecoder().decode(text.toString());
  }

  /**
   * 字节转为Base64文本
   *
   * @param bytes 字节数组
   * @return Base64文本
   */
  public String encodeBase64(byte[] bytes) {
    byte[] base64Bytes = Base64.getEncoder().encode(bytes);
    return new String(base64Bytes, StandardCharsets.UTF_8);
  }
}
