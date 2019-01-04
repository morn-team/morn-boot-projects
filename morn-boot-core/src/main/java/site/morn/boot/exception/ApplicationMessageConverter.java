package site.morn.boot.exception;

import site.morn.bean.annotation.Target;
import site.morn.exception.ApplicationMessage;
import site.morn.exception.ApplicationMessages;
import site.morn.rest.RestBuilders;
import site.morn.rest.RestMessage;
import site.morn.rest.convert.RestConverter;

/**
 * 应用消息转换器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/27
 */
@Target(ApplicationMessage.class)
public class ApplicationMessageConverter implements RestConverter<ApplicationMessage> {

  @Override
  public ApplicationMessage convert(RestMessage restMessage) {
    return ApplicationMessages.buildMessage(restMessage.getCode(), restMessage.getMessage(), null);
  }

  @Override
  public RestMessage revert(ApplicationMessage applicationMessage) {
    return RestBuilders.errorBuilder().code(applicationMessage.getCode())
        .message(applicationMessage.getMessage()).build();
  }
}
