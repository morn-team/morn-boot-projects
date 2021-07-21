package site.morn.boot.exception.interpreter;

import lombok.experimental.UtilityClass;

/**
 * 异常解释器常量
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/23
 */
@UtilityClass
public class InterpreterConstants {

  /**
   * 异常常量
   */
  public static class Errors {

    /**
     * 登录失败-未知错误
     */
    public static final String LOGIN_FAILURE = "login.failure";

    /**
     * 用户不存在
     */
    public static final String LOGIN_UNKNOWN_ACCOUNT = "login.unknown-account";

    /**
     * 密码错误
     */
    public static final String LOGIN_PASSWORD_INCORRECT = "login.password-incorrect";

    /**
     * 账户已锁定
     */
    public static final String LOGIN_ACCOUNT_LOCKED = "login.account-locked";

    private Errors() {
      throw new UnsupportedOperationException(
          "This is a constant class and cannot be instantiated");
    }
  }
}
