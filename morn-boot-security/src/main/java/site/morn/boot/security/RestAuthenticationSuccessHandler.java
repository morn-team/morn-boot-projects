package site.morn.boot.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import site.morn.boot.rest.RestBuilders;
import site.morn.boot.web.Responses;
import site.morn.rest.RestMessage;

/**
 * 认证成功处理者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/29
 */
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    RestMessage message = RestBuilders.successMessage("login.success");
    Responses.from(response).json().respond(message);
  }
}
