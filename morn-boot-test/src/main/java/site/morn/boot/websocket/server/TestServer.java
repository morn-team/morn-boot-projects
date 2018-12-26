package site.morn.boot.websocket.server;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * site.morn.boot.websocket.server
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/26
 */
@Slf4j
@ServerEndpoint("/socket")
@Component
public class TestServer {

  public TestServer() {
  }

  @OnOpen
  public void onOpen(Session session) {
    log.info("Client onOpen: " + session);
  }

  @OnMessage
  public void onMessage(String message) {
    log.info("Client onMessage: " + message);
  }

  @OnClose
  public void onClose() {
    log.info("Client onClose.");
  }
}
