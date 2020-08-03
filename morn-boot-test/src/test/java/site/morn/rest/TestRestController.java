package site.morn.rest;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.morn.rest.annotation.RestResponse;
import site.morn.test.TestUser;

/**
 * 测试REST控制器
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/26
 */
@RestResponse
@RestController
@RequestMapping("test/rest")
public class TestRestController {

  /**
   * 返回用户数据
   */
  @GetMapping("data")
  public TestUser returnData() {
    return new TestUser(1L, "Timely");
  }

  /**
   * 抛异常
   */
  @GetMapping("ex")
  public SerialMessage returnException() {
    throw new RuntimeException("This is exception.");
  }

  /**
   * 返回用户数据 - 百度格式
   */
  @GetMapping("baidu/data")
  @RestResponse(BaiduMessage.class)
  public TestUser returnBaiduData() {
    return new TestUser(2L, "Baidu");
  }

  /**
   * 抛异常 - 百度格式
   */
  @GetMapping("baidu/ex")
  @RestResponse(BaiduMessage.class)
  public SerialMessage returnBaiduException() {
    throw new RuntimeException("This is Baidu exception.");
  }

  /**
   * 返回用户数据 - Morn格式
   */
  @GetMapping("morn/data")
  @RestResponse(RestMessage.class)
  public BaiduMessage returnMornData() {
    TestUser user = new TestUser(3L, "Morn");
    BaiduMessage baiduMessage = new BaiduMessage();
    baiduMessage.setError("0");
    baiduMessage.setData(user);
    return baiduMessage;
  }

  /**
   * 抛异常 - 可解释异常
   */
  @GetMapping("resolvable/ex")
  @RestResponse
  public void returnResolvableException(@Valid TestUser user) {
  }
}
