package site.morn.exception;

import site.morn.bean.annotation.Target;
import site.morn.translate.Transfer;
import site.morn.translate.TranslateConverter;

/**
 * 测试应用消息转换器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/11
 */
//@Component
@Target(ApplicationMessage.class)
public class TestApplicationMessageConverter implements TranslateConverter<ApplicationMessage> {

  @Override
  public ApplicationMessage convert(Transfer transfer) {
    // 构建应用消息
    return ApplicationMessages
        .buildMessage(transfer.getCode(), "This is message.", "This is solution.");
  }
}
