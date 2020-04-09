package site.morn.boot.message;


import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Function;
import site.morn.boot.message.annotation.MessageTopic;
import site.morn.boot.message.annotation.MessageType;
import site.morn.test.TestUser;

@Slf4j
@Component
@MessageTopic("user-data")
public class TestUserMessageHandler {

  @Function("add")
  @MessageType("add")
  public void addUser(TestUser testUser, BroadcastMessageHeaders headers) {
    Assert.assertNotNull(headers);
    log.info("Message id {}", headers.getId());
    Assert.assertNotNull(testUser);
    log.info("Add user: {}", testUser.getUsername());
  }

  @Function("add")
  @MessageType("update")
  public void updateUser(TestUser testUser, BroadcastMessageHeaders headers) {
    Assert.assertNotNull(headers);
    log.info("Message id {}", headers.getId());
    Assert.assertNotNull(testUser);
    log.info("Update user: {}", testUser.getUsername());
  }
}
