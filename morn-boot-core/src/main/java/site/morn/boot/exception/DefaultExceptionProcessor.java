package site.morn.boot.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import site.timely.exception.ExceptionInterpreter;
import site.timely.exception.ExceptionInterpreterCache;
import site.timely.exception.ExceptionMessage;
import site.timely.exception.ExceptionProcessor;

/**
 * 默认异常处理器
 *
 * @author timely-rain
 * @version 1.0.0, 2018/10/17
 * @since 1.0
 */
@AllArgsConstructor
public class DefaultExceptionProcessor implements ExceptionProcessor {

  private ExceptionInterpreterCache interpreterCache;

  @Override
  public <T extends Exception> ExceptionMessage process(T exception) {
    List<ExceptionInterpreter> exceptionInterpreters = interpreterCache.find(exception.getClass());
    if (CollectionUtils.isEmpty(exceptionInterpreters)) {
      return null;
    }
    ExceptionInterpreter exceptionInterpreter = exceptionInterpreters.get(0);
    return exceptionInterpreter.resolve(exception);
  }
}
