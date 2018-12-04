package site.morn.boot.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
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
  private IdentifiedBeanCache beanCache;

  @Override
  public <T extends Exception> ExceptionMessage process(T exception) {
    // 从缓存中获取异常解释器
    List<ExceptionInterpreter> exceptionInterpreters = beanCache
        .beans(ExceptionInterpreter.class, exception.getClass());
    if (CollectionUtils.isEmpty(exceptionInterpreters)) {
      return null;
    }
    // 默认使用第一个解释器处理异常
    ExceptionInterpreter exceptionInterpreter = exceptionInterpreters.get(0);
    return exceptionInterpreter.resolve(exception);
  }
}
