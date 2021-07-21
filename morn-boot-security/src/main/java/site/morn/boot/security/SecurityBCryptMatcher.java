package site.morn.boot.security;

import static site.morn.cipher.support.AlgorithmNameConstants.SPRING_B_CRYPT;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import site.morn.cipher.AlgorithmMatcher;
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
@Slf4j
@AlgorithmName(SPRING_B_CRYPT)
public class SecurityBCryptMatcher extends SimpleAlgorithmHolder implements AlgorithmMatcher {

  @Override
  public boolean matches(CharSequence rawText, CharSequence encodedText) {
    try {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder()
          .matches(rawText, encodedText.toString());
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
      return false;
    }
  }
}
