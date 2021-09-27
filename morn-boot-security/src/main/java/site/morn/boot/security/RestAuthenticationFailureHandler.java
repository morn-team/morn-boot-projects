package site.morn.boot.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import site.morn.boot.rest.RestBuilders;
import site.morn.boot.web.Responses;
import site.morn.rest.RestMessage;

/**
 * 认证失败处理者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/29
 */
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) {
    RestMessage message = RestBuilders.failureBuilder().code("login.failure")
        .message(exception.getMessage()).build();
    Responses.from(response).json().respond(message);
  }
}
