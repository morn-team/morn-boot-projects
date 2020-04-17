package site.morn.boot.message.rocket;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.boot.message.BroadcastMessage;
import site.morn.boot.message.support.BroadcastMessageBuilder;

/**
 * Rocket目标单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/18
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RocketDestinationConverterTest {

  @Autowired
  private RocketDestinationConverter destinationConverter;

  @Test
  public void convert() {
    BroadcastMessage<Object> message = BroadcastMessageBuilder.withPayload(new Object())
        .setTopic("testTopic").build();
    String destination = destinationConverter.convert(message);
    Assert.assertEquals("testTopic", destination);
  }
}