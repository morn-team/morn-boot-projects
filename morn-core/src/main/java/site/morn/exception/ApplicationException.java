package site.morn.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

/**
 * 应用异常
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/9
 */
@RequiredArgsConstructor
@Getter
@Setter
public class ApplicationException extends RuntimeException {

  /**
   * 异常消息
   */
  @Delegate
  private final transient ApplicationMessage applicationMessage;
}
