package site.morn.boot.exception;

import site.timely.exception.ExceptionInterpreter;
import site.timely.exception.ExceptionMessage;
import site.timely.tag.annotation.Tag;

/**
 * 默认异常解释器
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/21
 * @since 1.0
 */
@Tag
public class DefaultExceptionInterpreter implements ExceptionInterpreter {

  @Override
  public ExceptionMessage resolve(Throwable throwable) {
    return null;
  }
}
