package site.morn.boot.validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * site.morn.boot.validator
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/19
 * @since 1.0
 */
@Log
@Component
public class ValidateExceptionResolver {

  @ExceptionHandler(BindException.class)
  @ResponseBody
  public Object catchException(HttpServletRequest request, HttpServletResponse response,
      BindException e) {
    log.info("ValidateExceptionResolver.catchException");
    return e.getFieldError();
  }
}
