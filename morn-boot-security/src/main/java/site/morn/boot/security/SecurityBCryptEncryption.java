package site.morn.boot.security;

import static site.morn.constant.DigestConstant.Algorithms.SPRING_B_CRYPT;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import site.morn.bean.annotation.Tag;
import site.morn.digest.Encryption;

/**
 * BCrypt加密
 *
 * <p>基于SpringSecurity加密，格式参考SpringSecurity官方文档
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
@Tag(SPRING_B_CRYPT)
public class SecurityBCryptEncryption implements Encryption {

  @Override
  public String encrypt(CharSequence text) {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(text);
  }
}
