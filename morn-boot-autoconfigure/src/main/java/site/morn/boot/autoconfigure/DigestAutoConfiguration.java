package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.boot.digest.MD5Algorithms.MD5Encryption;
import site.morn.boot.digest.MD5Algorithms.MD5Matcher;
import site.morn.boot.security.SecurityBCryptEncryption;
import site.morn.boot.security.SecurityBCryptMatcher;
import site.morn.digest.annotation.DigestAlgorithm;

/**
 * 数据加密自动化配置
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
@Configuration
public class DigestAutoConfiguration implements BeanConfigurer {

  /**
   * 注册加密算法注解
   *
   * @param registry 实例注解注册表
   */
  @Override
  public void addBeanAnnotations(AnnotationFieldRegistry registry) {
    registry.add(DigestAlgorithm.class);
  }

  /**
   * 注册MD5加密算法
   */
  @Bean
  @ConditionalOnMissingBean
  public MD5Encryption md5Encryption() {
    return new MD5Encryption();
  }

  /**
   * 注册MD5校验算法
   */
  @Bean
  @ConditionalOnMissingBean
  public MD5Matcher md5Matcher() {
    return new MD5Matcher();
  }

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
