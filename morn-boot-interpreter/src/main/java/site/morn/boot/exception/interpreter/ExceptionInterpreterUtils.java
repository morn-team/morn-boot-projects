package site.morn.boot.exception.interpreter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

/**
 * 校验异常解释工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/29
 */
@UtilityClass
class ExceptionInterpreterUtils {

  /**
   * 生成错误信息
   *
   * @param errors 属性错误
   * @return 错误信息
   */
  public static String generateMessages(List<FieldError> errors) {
    List<String> messages = errors.stream().map(ExceptionInterpreterUtils::generateMessage)
        .collect(Collectors.toList());
    return StringUtils.collectionToCommaDelimitedString(messages);
  }

  /**
   * 生成错误信息
   *
   * @param fieldError 属性错误
   * @return 错误信息
   */
  public static String generateMessage(FieldError fieldError) {
    return fieldError.getField() + " " + fieldError.getDefaultMessage();
  }
}
