package site.morn.boot.security;

import static site.morn.cipher.support.AlgorithmNameConstants.SPRING_B_CRYPT;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import site.morn.cipher.AlgorithmEncryption;
import site.morn.cipher.annotation.AlgorithmName;
import site.morn.cipher.support.SimpleAlgorithmHolder;

/**
 * BCrypt加密
 *
 * <p>基于SpringSecurity加密，格式参考SpringSecurity官方文档
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
@AlgorithmName(SPRING_B_CRYPT)
public class SecurityBCryptEncryption extends SimpleAlgorithmHolder implements AlgorithmEncryption {

  @Override
  public String encrypt(CharSequence text) {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(text);
  }
}
