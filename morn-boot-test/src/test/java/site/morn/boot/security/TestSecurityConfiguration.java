package site.morn.boot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

/**
 * 安全认证配置
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/28
 */
@Configuration
@EnableWebSecurity
public class TestSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    // 禁止匿名用户
    http.anonymous().disable()
        // 禁止csrf
        .csrf().disable()
        // 认证失败处理
        .exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        // 表单登录配置
        .and().formLogin()
        //   登录成功处理
        .successHandler(new RestAuthenticationSuccessHandler())
        //   登录失败处理
        .failureHandler(new RestAuthenticationFailureHandler());
  }
}
