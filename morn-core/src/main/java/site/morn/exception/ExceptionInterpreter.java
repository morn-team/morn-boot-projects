package site.morn.exception;

/**
 * 异常解释器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/19
 */
@FunctionalInterface
public interface ExceptionInterpreter {

  /**
   * 解释异常
   *
   * @param throwable 异常
   * @param args      参数
   * @return 应用消息
   */
  ApplicationMessage interpret(Throwable throwable, Object... args);
}
