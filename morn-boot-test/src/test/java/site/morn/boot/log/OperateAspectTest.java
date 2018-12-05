package site.morn.boot.log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * site.morn.boot.log
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class OperateAspectTest {

  @Autowired
  private TestUserController userController;

  @Test
  public void aroundOperate1() {
    CountDownLatch countDownLatch = new CountDownLatch(1);

    Map<String, Object> user = new HashMap<>();
    user.put("username", "timely");
    user.put("password", "123456");

    new Thread(() -> {
      try {
        userController.addUser(user);
      } finally {
        countDownLatch.countDown();
      }
    }).start();

    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
    }
  }

  @Test
  public void aroundOperate2() {
    CountDownLatch countDownLatch = new CountDownLatch(1);

    Map<String, Object> user = new HashMap<>();
    user.put("username", "rain");
    user.put("password", "654321");

    new Thread(() -> {
      try {
        userController.updateUser(user);
      } finally {
        countDownLatch.countDown();
      }
    }).start();

    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
    }
  }
}