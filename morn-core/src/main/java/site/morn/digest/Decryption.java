package site.morn.digest;

/**
 * 解密者
 *
 * @author timely-rain
 * @since 2.1.0, 2019/8/30
 */
@FunctionalInterface
public interface Decryption {

  /**
   * 解密
   */
  String decrypt(String text);
}
