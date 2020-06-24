package site.morn.boot.security;

import static site.morn.constant.DigestConstant.Algorithms.SPRING_B_CRYPT;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import site.morn.digest.DigestMatcher;
import site.morn.digest.annotation.DigestAlgorithm;

/**
 * BCrypt加密
 *
 * <p>基于SpringSecurity加密，格式参考SpringSecurity官方文档
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
@Slf4j
@DigestAlgorithm(SPRING_B_CRYPT)
public class SecurityBCryptMatcher implements DigestMatcher {

  @Override
  public boolean matches(CharSequence rawText, String encodedText) {
    try {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder()
          .matches(rawText, encodedText);
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
      return false;
    }
  }
}
