package site.morn.boot.exception.interpreter;

import static site.morn.boot.exception.interpreter.InterpreterConstants.Errors.LOGIN_ACCOUNT_LOCKED;
import static site.morn.boot.exception.interpreter.InterpreterConstants.Errors.LOGIN_FAILURE;
import static site.morn.boot.exception.interpreter.InterpreterConstants.Errors.LOGIN_PASSWORD_INCORRECT;
import static site.morn.boot.exception.interpreter.InterpreterConstants.Errors.LOGIN_UNKNOWN_ACCOUNT;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
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
      return ApplicationMessages.translateMessage(LOGIN_UNKNOWN_ACCOUNT);
    }
    if (exception instanceof IncorrectCredentialsException) {
      log.debug("登录失败，密码错误。");
      return ApplicationMessages.translateMessage(LOGIN_PASSWORD_INCORRECT);
    }
    if (exception instanceof LockedAccountException) {
      log.debug("登录失败，账号锁定。");
      return ApplicationMessages.translateMessage(LOGIN_ACCOUNT_LOCKED);
    }
    return ApplicationMessages.translateMessage(LOGIN_FAILURE);
  }
}
