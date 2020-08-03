package site.morn.boot.exception.interpreter;

import site.morn.bean.annotation.Target;
import site.morn.exception.ApplicationException;
import site.morn.exception.ApplicationMessage;
import site.morn.exception.ExceptionInterpreter;

/**
 * 应用异常解释器
 *
 * <p>处理Morn相关异常
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/12
 */
@Target(ApplicationException.class)
public class ApplicationExceptionInterpreter implements ExceptionInterpreter {

  @Override
  public ApplicationMessage interpret(Throwable throwable, Object... args) {
    ApplicationException applicationException = (ApplicationException) throwable;
    return applicationException.getApplicationMessage();
  }
}
