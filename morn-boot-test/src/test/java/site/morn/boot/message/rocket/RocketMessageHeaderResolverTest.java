package site.morn.boot.message.rocket;

import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;
import site.morn.boot.message.BroadcastMessageHeaders;

/**
 * Rocket消息头单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/18
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RocketMessageHeaderResolverTest {

  @Autowired
  private RocketMessageHeaderResolver headerResolver;

  @Test
  public void convert() {
    MessageExt messageExt = new MessageExt();
    messageExt.setTopic("testTopic");
    messageExt.putUserProperty(CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);
    BroadcastMessageHeaders headers = headerResolver.convert(messageExt);
    Assert.assertEquals("testTopic", headers.getTopic());
  }
}