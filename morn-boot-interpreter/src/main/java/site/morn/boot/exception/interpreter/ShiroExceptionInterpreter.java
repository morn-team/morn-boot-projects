package site.morn.boot.exception.interpreter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import site.morn.bean.annotation.Target;
import site.morn.exception.ApplicationMessage;
import site.morn.exception.ApplicationMessages;
import site.morn.exception.ExceptionInterpreter;

/**
 * Shiro异常解释器
 *
 * <p>处理Shiro相关异常
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/25
 */
@Slf4j
@Target(AuthenticationException.class)
public class ShiroExceptionInterpreter implements ExceptionInterpreter {

  @Override
  public ApplicationMessage interpret(Throwable throwable, Object... args) {
    AuthenticationException exception = (AuthenticationException) throwable;
    if (exception instanceof UnknownAccountException) {
      log.debug("登录失败，账号不存在。");
      return ApplicationMessages.translateMessage("login.unknown-account");
    }
    if (exception instanceof IncorrectCredentialsException) {
      log.debug("登录失败，密码错误。");
      return ApplicationMessages.translateMessage("login.password-incorrect");
    }
    return ApplicationMessages.translateMessage("login.failure");
  }
}
