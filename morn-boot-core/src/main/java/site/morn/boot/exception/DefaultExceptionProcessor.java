package site.morn.boot.exception;

import java.util.Objects;
import lombok.AllArgsConstructor;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionMessage;
import site.morn.exception.ExceptionProcessor;

/**
 * 默认异常处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/10/17
 */
@AllArgsConstructor
public class DefaultExceptionProcessor implements ExceptionProcessor {

  /**
   * 异常解释器缓存
   */
  private final IdentifiedBeanCache beanCache;

  @Override
  public <T extends Exception> ExceptionMessage process(T exception) {
    // 从缓存中获取异常解释器
    ExceptionInterpreter exceptionInterpreter = beanCache
        .bean(ExceptionInterpreter.class, exception.getClass());
    if (Objects.isNull(exceptionInterpreter)) {
      return null;
    }
    return exceptionInterpreter.resolve(exception);
  }
}
