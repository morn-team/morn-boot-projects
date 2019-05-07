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
 * 应用消息构建器单元测试
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
  public void buildException() {
    try {
      ApplicationException applicationException = ApplicationMessages
          .buildException("login.password-is-null", "登录密码不能为空", "请输入登录密码");
      Assert.assertNotNull("应用异常构建成功", applicationException);
      Assert.assertEquals("login.password-is-null", applicationException.getCode());
      Assert.assertEquals("登录密码不能为空", applicationException.getMessage());
      Assert.assertEquals("请输入登录密码", applicationException.getSolution());
      throw applicationException;
    } catch (ApplicationException e) {
      log.error("状态码：{}", e.getCode());
      // 状态码：login.password-is-null

      log.error("解决方案：{}", e.getSolution());
      // 解决方案：请输入登录密码

      log.error(e.getMessage(), e);
      // site.morn.exception.ApplicationExceptin: 登录密码不能为空
    }
  }

  @Test
  public void buildMessage() {
    ApplicationMessage applicationMessage = ApplicationMessages
        .buildMessage("login.password-is-null", "登录密码不能为空", "请输入登录密码");
    Assert.assertNotNull(applicationMessage);
    log.info(applicationMessage.toString());
    Assert.assertEquals("login.password-is-null", applicationMessage.getCode());
    Assert.assertEquals("登录密码不能为空", applicationMessage.getMessage());
    Assert.assertEquals("请输入登录密码", applicationMessage.getSolution());
  }

  @Test
  public void translateException() {
    try {
      ApplicationException applicationException = ApplicationMessages
          .translateException("login.password-is-null");
      Assert.assertNotNull("应用异常构建成功", applicationException);
      throw applicationException;
    } catch (ApplicationException e) {
      log.error("状态码：{}", e.getCode());
      // 状态码：login.password-is-null

      log.error("解决方案：{}", e.getSolution());
      // 解决方案：请输入登录密码

      log.error(e.getMessage(), e);
      // site.morn.exception.ApplicationExceptin: 登录密码不能为空
    }
  }

  @Test
  public void translateMessage() {
    ApplicationMessage message = ApplicationMessages.translateMessage("login.password-is-null");
    Assert.assertNotNull("应用消息构建失败", message);
    log.info(message.toString());
    Assert.assertNotNull(message.getSolution());
  }
}
