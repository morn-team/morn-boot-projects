package site.morn.boot.exception.interpreter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import site.morn.bean.annotation.Target;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionMessage;

/**
 * 数据绑定异常解释器
 *
 * <p>处理Spring validation 相关异常
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/21
 */
@Target(BindException.class)
public class BindExceptionInterpreter implements ExceptionInterpreter {

  @Override
  public ExceptionMessage resolve(Throwable throwable) {
    BindException bindException = (BindException) throwable;
    // 获取全部属性错误
    List<FieldError> errors = bindException.getFieldErrors();
    // 生成错误消息文本
    List<String> messages = errors.stream().map(this::generateMessage).collect(Collectors.toList());
    // 构建异常消息
    return ExceptionMessage.builder()
        .message(StringUtils.collectionToCommaDelimitedString(messages)).build();
  }

  /**
   * 生成错误信息
   *
   * @param fieldError 属性错误
   * @return 错误信息
   */
  private String generateMessage(FieldError fieldError) {
    return fieldError.getObjectName() + "." + fieldError.getField() + fieldError
        .getDefaultMessage();
  }
}
