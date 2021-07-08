package site.morn.exception;

import static site.morn.exception.ApplicationMessages.FAILURE;

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
  public void testBuildException() {
    ApplicationException applicationException = ApplicationMessages
        .buildException("登录密码不能为空");
    Assert.assertNotNull(applicationException);
    Assert.assertEquals(FAILURE, applicationException.getCode());
    Assert.assertEquals("登录密码不能为空", applicationException.getMessage());
    Assert.assertNull(applicationException.getSolution());
  }

  @Test
  public void testBuildException1() {
    ApplicationException applicationException = ApplicationMessages
        .buildException("login.password-is-null", "登录密码不能为空");
    Assert.assertNotNull(applicationException);
    Assert.assertEquals("login.password-is-null", applicationException.getCode());
    Assert.assertEquals("登录密码不能为空", applicationException.getMessage());
    Assert.assertNull(applicationException.getSolution());
  }

  @Test
  public void testBuildException2() {
    ApplicationException applicationException = ApplicationMessages
        .buildException("login.password-is-null", "登录密码不能为空", "请输入登录密码");
    Assert.assertNotNull(applicationException);
    Assert.assertEquals("login.password-is-null", applicationException.getCode());
    Assert.assertEquals("登录密码不能为空", applicationException.getMessage());
    Assert.assertEquals("请输入登录密码", applicationException.getSolution());
  }

  @Test
  public void testBuildMessage() {
    ApplicationMessage applicationMessage = ApplicationMessages
        .buildMessage("登录密码不能为空");
    Assert.assertNotNull(applicationMessage);
    Assert.assertEquals(FAILURE, applicationMessage.getCode());
    Assert.assertEquals("登录密码不能为空", applicationMessage.getMessage());
    Assert.assertNull(applicationMessage.getSolution());
  }

  @Test
  public void testBuildMessage1() {
    ApplicationMessage applicationMessage = ApplicationMessages
        .buildMessage("login.password-is-null", "登录密码不能为空");
    Assert.assertNotNull(applicationMessage);
    Assert.assertEquals("login.password-is-null", applicationMessage.getCode());
    Assert.assertEquals("登录密码不能为空", applicationMessage.getMessage());
    Assert.assertNull(applicationMessage.getSolution());
  }

  @Test
  public void testBuildMessage2() {
    ApplicationMessage applicationMessage = ApplicationMessages
        .buildMessage("login.password-is-null", "登录密码不能为空", "请输入登录密码");
    Assert.assertNotNull(applicationMessage);
    Assert.assertEquals("login.password-is-null", applicationMessage.getCode());
    Assert.assertEquals("登录密码不能为空", applicationMessage.getMessage());
    Assert.assertEquals("请输入登录密码", applicationMessage.getSolution());
  }

  @Test
  public void translateException() {
    ApplicationException applicationException = ApplicationMessages
        .translateException("login.password-is-null");
    Assert.assertNotNull("应用异常构建成功", applicationException);
    Assert.assertNotNull(applicationException.getMessage());
    Assert.assertNotNull(applicationException.getSolution());
  }

  @Test
  public void translateMessage() {
    ApplicationMessage message = ApplicationMessages.translateMessage("login.password-is-null");
    Assert.assertNotNull("应用消息构建失败", message);
    Assert.assertNotNull(message.getMessage());
    Assert.assertNotNull(message.getSolution());
  }
}
