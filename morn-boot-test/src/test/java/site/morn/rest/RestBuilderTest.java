package site.morn.rest;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.bean.IdentifiedBeanHolder;
import site.morn.bean.annotation.Target;
import site.morn.boot.bean.IdentifiedBeanPostProcessor;
import site.morn.rest.convert.RestConverter;

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

  @Autowired
  private IdentifiedBeanCache beanCache;

  @Test
  public void beforeMethod() {
    BaiduConverter baiduConverter = new BaiduConverter();
    IdentifiedBeanHolder<BaiduConverter> beanHolder = IdentifiedBeanPostProcessor
        .generateBeanHolder(baiduConverter);
    beanCache.cache(beanHolder);
  }

  @Test
  public void successMessage() {
    RestMessage restMessage = RestBuilders.successMessage();
    log.info(restMessage.toString());
    Assert.assertEquals("success", restMessage.getCode());
  }

  @Test
  public void successMessage1() {
    RestMessage restMessage = RestBuilders.successMessage("Success");
    log.info(restMessage.toString());
    Assert.assertEquals("Success", restMessage.getCode());
  }

  @Test
  public void successMessage2() {
    Object data = new Object();
    RestMessage restMessage = RestBuilders.successMessage(data);
    log.info(restMessage.toString());
    Assert.assertEquals(data, restMessage.getData());
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

  /**
   * 百度REST消息
   */
  @Getter
  @Setter
  @ToString
  public class BaiduMessage {

    /**
     * 状态码
     */
    private String error;

    /**
     * 消息内容
     */
    private String msg;
  }

  /**
   * 百度消息转换器
   */
  @Target(BaiduMessage.class)
  public class BaiduConverter implements RestConverter<BaiduMessage> {

    @Override
    public BaiduMessage convert(RestMessage restMessage) {
      BaiduMessage baiduMessage = new BaiduMessage();
      baiduMessage.setError(restMessage.getCode());
      baiduMessage.setMsg(restMessage.getMessage());
      return baiduMessage;
    }

    @Override
    public RestMessage revert(BaiduMessage baiduMessage) {
      RestMessage restMessage = new SimpleRestMessage();
      restMessage.setSuccess(isSuccess(baiduMessage));
      restMessage.setCode(baiduMessage.getError());
      restMessage.setMessage(baiduMessage.getMsg());
      return restMessage;
    }

    private boolean isSuccess(BaiduMessage baiduMessage) {
      return Objects.equals(baiduMessage.getError(), "0");
    }
  }
}