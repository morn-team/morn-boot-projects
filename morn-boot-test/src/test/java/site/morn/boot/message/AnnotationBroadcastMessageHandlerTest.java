package site.morn.boot.message;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.test.TestUser;

/**
 * 注解消息处理者单元测试
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AnnotationBroadcastMessageHandlerTest {

  @Autowired
  private AnnotationBroadcastMessageHandler messageHandler;

  @Test
  public void handleMessage() {
    TestUser testUser = new TestUser();
    testUser.setUsername("timely-rain");
    BroadcastMessageHeaderAccessor headerAccessor = new BroadcastMessageHeaderAccessor();
    headerAccessor.setTopic("user-data");
    headerAccessor.setType("add");
    SimpleBroadcastMessage<TestUser> message = new SimpleBroadcastMessage<TestUser>(testUser,
        headerAccessor.toMessageHeaders());
    try {
      messageHandler.handleMessage(message);
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }

  }
}