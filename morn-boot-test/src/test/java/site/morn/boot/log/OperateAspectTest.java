package site.morn.boot.log;

import java.util.HashMap;
import java.util.Map;
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
@SpringBootTest
public class OperateAspectTest {

  @Autowired
  private TestUserController userController;

  @Test
  public void aroundOperate1() {
    Map<String, Object> user = new HashMap<>();
    user.put("username", "timely");
    user.put("password", "123456");
    userController.addUser(user);
  }

  @Test
  public void aroundOperate2() {
    Map<String, Object> user = new HashMap<>();
    user.put("username", "rain");
    user.put("password", "654321");
    userController.updateUser(user);
  }
}