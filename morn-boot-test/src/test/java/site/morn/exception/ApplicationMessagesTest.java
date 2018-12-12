package site.morn.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 警告构建器单元测试
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/10
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationMessagesTest {

  @Test
  public void message() {
    ApplicationMessage message = ApplicationMessages.translate("login.password-is-null");
    Assert.assertNotNull("异常消息构建失败", message);
    log.info(message.toString());
    Assert.assertNotNull(message.getSolution());
  }

  @Test
  public void thrown() {
    ApplicationMessage message = ApplicationMessages.translate("login.password-is-null");
    try {
      message.thrown();
      Assert.fail("异常构建失败");
    } catch (ApplicationException e) {
      Assert.assertNotNull("异常构建成功：", e.getMessage());
      log.error(e.getMessage(), e);
      log.error("状态码：{}", e.getCode());
      log.error("解决方案：{}", e.getSolution());
    }
  }
}
