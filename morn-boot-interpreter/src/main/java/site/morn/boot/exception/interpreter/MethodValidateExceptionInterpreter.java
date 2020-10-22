package site.morn.boot.exception.interpreter;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import site.morn.bean.annotation.Target;
import site.morn.exception.ApplicationMessage;
import site.morn.exception.ApplicationMessages;
import site.morn.exception.ExceptionInterpreter;
import site.morn.util.GenericUtils;

/**
 * 校验异常解释器
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/29
 */
@Slf4j
@Target(MethodArgumentNotValidException.class)
public class MethodValidateExceptionInterpreter implements ExceptionInterpreter {

  @Override
  public ApplicationMessage interpret(Throwable throwable, Object... args) {
    MethodArgumentNotValidException exception = GenericUtils.castFrom(throwable);
    // 生成错误消息文本
    List<FieldError> errors = exception.getBindingResult().getFieldErrors();
    String message = ExceptionInterpreterUtils.generateMessages(errors);
    // 构建异常消息
    return ApplicationMessages.buildMessage("validate", message, null);
  }
}
