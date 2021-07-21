package site.morn.boot.log;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Duration.TEN_SECONDS;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.log.OperateMeta;
import site.morn.log.Operation;

/**
 * 操作日志单元测试
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class OperateAspectTest {

  /**
   * 完成标识
   */
  public static final AtomicBoolean DONE = new AtomicBoolean();

  @Autowired
  private TestOperateController userController;

  @Autowired
  private I18nOperationConverter i18nOperationConverter;

  @Before
  public void setUp() throws Exception {
    DONE.set(false);
  }

  @Test
  public void aroundOperate1() {
    Map<String, Object> user = new HashMap<>();
    user.put("username", "Caramel");
    user.put("password", "123456");

    userController.addUser(user);

    await().timeout(TEN_SECONDS).untilTrue(DONE);
  }

  @Test
  public void aroundOperate2() {
    Map<String, Object> user = new HashMap<>();
    user.put("username", "Mocha");
    user.put("age", "16");

    try {
      userController.updateUser(user);
    } catch (Exception ignored) {
    }

    await().timeout(TEN_SECONDS).untilTrue(DONE);
  }

  @Test
  public void i18nAddTest() {
    String[] args = {"Latte"};
    OperateMeta operateMeta = new OperateMeta();
    operateMeta.setGroupName("user");
    operateMeta.setActionName("add");
    operateMeta.setActionArgs(args);
    Operation operation = i18nOperationConverter.convert(operateMeta);
    Assert.assertNotNull(operation);
    Assert.assertEquals("新增用户：Latte", operation.getContent());
  }

  @Test
  public void i18nUpdateTest() {
    String[] args = {"Latte", "19"};
    OperateMeta operateMeta = new OperateMeta();
    operateMeta.setGroupName("user");
    operateMeta.setActionName("update");
    operateMeta.setActionArgs(args);
    Operation operation = i18nOperationConverter.convert(operateMeta);
    Assert.assertNotNull(operation);
    Assert.assertEquals("更新用户：Latte，年龄：19", operation.getContent());
  }
}