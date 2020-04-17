package site.morn.boot.message.rocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.boot.message.MessageResult;
import site.morn.boot.message.constant.MessageConstant.MessageResultStatus;

/**
 * Rocket结果单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/18
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RocketMessageResultConverterTest {

  @Autowired
  private RocketMessageResultConverter resultConverter;

  private final SendResult sendResult = new SendResult();

  @Test
  public void success() {
    sendResult.setSendStatus(SendStatus.SEND_OK);
    MessageResult result1 = resultConverter.convert(sendResult);
    Assert.assertEquals(MessageResultStatus.SUCCESS, result1.getStatus());
  }

  @Test
  public void failure1() {
    sendResult.setSendStatus(SendStatus.FLUSH_DISK_TIMEOUT);
    MessageResult result1 = resultConverter.convert(sendResult);
    Assert.assertEquals(MessageResultStatus.FAILURE, result1.getStatus());
  }

  @Test
  public void failure2() {
    sendResult.setSendStatus(SendStatus.FLUSH_SLAVE_TIMEOUT);
    MessageResult result1 = resultConverter.convert(sendResult);
    Assert.assertEquals(MessageResultStatus.FAILURE, result1.getStatus());
  }

  @Test
  public void unknown() {
    sendResult.setSendStatus(SendStatus.SLAVE_NOT_AVAILABLE);
    MessageResult result1 = resultConverter.convert(sendResult);
    Assert.assertEquals(MessageResultStatus.UNKNOWN, result1.getStatus());
  }
}