package site.morn.digest;

/**
 * 加密者
 *
 * @author timely-rain
 * @since 2.1.0, 2019/8/30
 */
@FunctionalInterface
public interface Encryption {

  /**
   * 加密
   */
  String encrypt(CharSequence text);
}
