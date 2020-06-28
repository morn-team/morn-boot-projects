package site.morn.cipher;

/**
 * 解密者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
@FunctionalInterface
public interface Decryption {

  /**
   * 解密
   */
  String decrypt(CharSequence text);
}
