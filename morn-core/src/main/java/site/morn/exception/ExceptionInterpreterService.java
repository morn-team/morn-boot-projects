package site.morn.exception;

/**
 * 异常处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/10/16
 */
public interface ExceptionInterpreterService {

  /**
   * 解释异常
   *
   * @return 应用消息
   */
  <T extends Exception> ApplicationMessage interpret(T exception);
}
