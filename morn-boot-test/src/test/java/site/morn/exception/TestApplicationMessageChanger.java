package site.morn.exception;

import site.morn.bean.annotation.Target;
import site.morn.translate.Transfer;
import site.morn.translate.TranslateChanger;

/**
 * 测试应用消息转换器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/11
 */
//@Component
@Target(ApplicationMessage.class)
public class TestApplicationMessageChanger implements TranslateChanger<ApplicationMessage> {

  @Override
  public ApplicationMessage change(Transfer transfer) {
    // 构建应用消息
    return ApplicationMessage.builder().code(transfer.getCode()).message("This is message.")
        .solution("This is solution.").build();
  }
}
