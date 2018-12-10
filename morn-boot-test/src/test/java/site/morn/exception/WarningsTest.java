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
public class WarningsTest {

  @Test
  public void message() {
    Warning message = Warnings.message("login.password-is-null", "timely");
    Assert.assertNotNull("异常消息构建失败", message);
    log.info(message.toString());
    Assert.assertNotNull(message.getSolution());
  }
}
