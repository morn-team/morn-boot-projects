package site.morn.boot.websocket;

import java.io.IOException;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.boot.websocket.client.TestClient;
import site.morn.boot.websocket.client.TestStompClient;

/**
 * site.morn.boot.websocket
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebSocketTest {

  private static String uri = "ws://localhost/examples/websocket/echoAnnotation";
  private static Session session;
  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  private void start() {
    WebSocketContainer container = null;
    try {
      container = ContainerProvider.getWebSocketContainer();
    } catch (Exception ex) {
      System.out.println("error" + ex);
    }

    try {
      URI r = URI.create(uri);
      session = container.connectToServer(TestClient.class, r);
    } catch (DeploymentException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

//  @BeforeClass
//  public static void init() {
//    TestStompClient client = new TestStompClient();
//    client.startup();
//  }

  @Test
  public void sendToServer() {
    messagingTemplate.convertAndSend("/app/talk", "123");
  }

  @Test
  public void sendToClient() {
    TestStompClient client = new TestStompClient();
    client.startup();
    messagingTemplate.convertAndSend("/topic/talk", "123");
  }
}
