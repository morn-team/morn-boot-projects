package site.morn.boot.notify;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 系统通知测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class NotifyTest {

  @Autowired
  private TestNotifyBean notifyBean;

  @Test
  public void captchaToRoleNoTemplate() {
    try {
      notifyBean.captchaToRoleNoTemplate();
    } catch (Exception e) {
      Assert.fail();
    }
  }


  @Test
  public void captchaToUser() {
    try {
      notifyBean.captchaToUser();
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void captchaToRole() {
    try {
      notifyBean.captchaToRole();
    } catch (Exception e) {
      Assert.fail();
    }
  }
}