package site.morn.boot.exception;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.morn.bean.BeanPool;
import site.morn.exception.ApplicationMessage;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionInterpreterService;

/**
 * 默认异常处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/10/17
 */
@AllArgsConstructor
@Slf4j
public class DefaultExceptionInterpreterService implements ExceptionInterpreterService {

  /**
   * 异常解释器缓存
   */
  private final BeanPool beanPool;

  @Override
  public <T extends Exception> ApplicationMessage interpret(T exception) {
    // 从缓存中获取异常解释器
    ExceptionInterpreter exceptionInterpreter = beanPool
        .targetBean(ExceptionInterpreter.class, exception.getClass());
    if (Objects.isNull(exceptionInterpreter)) {
      log.debug("异常处理失败：尚未发现处理{}的异常解释器", exception.getClass().getSimpleName());
      return null;
    }
    return exceptionInterpreter.interpret(exception);
  }
}
