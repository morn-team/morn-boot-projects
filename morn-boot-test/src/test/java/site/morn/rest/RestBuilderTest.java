package site.morn.rest;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.boot.rest.RestBuilder;
import site.morn.boot.rest.RestBuilders;
import site.morn.rest.constant.RestMessageLevel;

/**
 * REST工具类单元测试
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/30
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestBuilderTest {

  @Test
  public void successMessage() {
    RestMessage restMessage = RestBuilders.successMessage();
    log.info(restMessage.toString());
    Assert.assertEquals("success", restMessage.getCode());
  }

  @Test
  public void successMessage1() {
    RestMessage restMessage = RestBuilders.successMessage("test");
    log.info(restMessage.toString());
    Assert.assertEquals("test", restMessage.getCode());
  }

  @Test
  public void successMessage2() {
    Object data = new Object();
    RestMessage restMessage = RestBuilders.successMessage(data);
    log.info(restMessage.toString());
    Assert.assertEquals(data, restMessage.getData());
  }

  @Test
  public void successMessage3() {
    Map<Object, Object> data = new HashMap<>();
    data.put("Foo", "Foo value");
    data.put("Bar", "Bar value");
    RestMessage restMessage = RestBuilders.infoBuilder().code("test").level(RestMessageLevel.WARNING)
        .message("This is test message.").data(data).build();
    log.info(restMessage.toString());
    Assert.assertEquals("This is test message.", restMessage.getMessage());
  }

  @Test
  public void failureMessage() {
    RestMessage restMessage = RestBuilders.failureMessage();
    log.info(restMessage.toString());
    Assert.assertEquals("failure", restMessage.getCode());
  }

  @Test
  public void failureMessage1() {
    RestMessage restMessage = RestBuilders.failureMessage("test");
    log.info(restMessage.toString());
    Assert.assertEquals("test", restMessage.getCode());
  }

  @Test
  public void from() {
    BaiduMessage baiduMessage = new BaiduMessage();
    baiduMessage.setError("0");
    baiduMessage.setMsg("操作成功");
    RestMessage restMessage = RestBuilder.from(baiduMessage);
    log.info(restMessage.toString());
    Assert.assertNotNull(restMessage);
  }

  @Test
  public void to() {
    BaiduMessage baiduMessage = RestBuilders.successBuilder().to(BaiduMessage.class);
    log.info(baiduMessage.toString());
    Assert.assertNotNull(baiduMessage);
  }

}