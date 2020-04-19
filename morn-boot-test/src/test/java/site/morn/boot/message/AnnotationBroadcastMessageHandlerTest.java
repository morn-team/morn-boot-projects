package site.morn.boot.message;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.boot.message.support.AnnotationBroadcastMessageHandler;
import site.morn.boot.message.support.BroadcastMessageBuilder;
import site.morn.test.TestUser;

/**
 * 注解消息处理者单元测试
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AnnotationBroadcastMessageHandlerTest {

  @Autowired
  private AnnotationBroadcastMessageHandler<?> messageHandler;

  @Test
  public void handleMessage() {
    // 创建消息数据
    TestUser testUser = new TestUser();
    testUser.setUsername("timely-rain");
    testUser.setPassword("password");
    // 构建消息
    Message<TestUser> message = BroadcastMessageBuilder.withPayload(testUser)
        .setTopic("userData")
        .setType("add")
        .setErrorChannelName("errorChannel")
        .setReplyChannelName("replyChannel")
        .setTag("developer")
        .setHeader("customHead", "foo").build();
    try {
      messageHandler.handleMessage(message);
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }

  }
}