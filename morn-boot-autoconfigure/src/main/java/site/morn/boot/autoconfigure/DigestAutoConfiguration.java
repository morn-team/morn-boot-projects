package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import site.morn.boot.security.SecurityBCryptEncryption;
import site.morn.boot.security.SecurityBCryptMatcher;

/**
 * 数据加密自动化配置
 *
 * @author timely-rain
 * @since 2.1.0, 2019/8/30
 */
@Configuration
public class DigestAutoConfiguration {


  /**
   * SpringSecurityBCrypt加密配置
   */
  @Configuration
  @ConditionalOnClass({BCryptPasswordEncoder.class, SecurityBCryptMatcher.class})
  public static class SecurityBCryptConfiguration {

    /**
     * 注册BCrypt加密
     */
    @Bean
    @ConditionalOnMissingBean
    public SecurityBCryptEncryption securityBCryptEncryption() {
      return new SecurityBCryptEncryption();
    }

    /**
     * 注册BCrypt校验
     */
    @Bean
    @ConditionalOnMissingBean
    public SecurityBCryptMatcher securityBCryptMatcher() {
      return new SecurityBCryptMatcher();
    }
  }
}
