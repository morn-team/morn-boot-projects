package site.morn.boot.websocket.client;

import java.util.ArrayList;
import java.util.List;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

/**
 * site.morn.boot.websocket.client
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/26
 */
@Slf4j
public class TestStompClient2 {

  public void startup() {
    try {
      List<Transport> transports = new ArrayList<>(1);

      WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
      webSocketContainer.setDefaultMaxTextMessageBufferSize(50 * 1024 * 1024);
      webSocketContainer.setDefaultMaxBinaryMessageBufferSize(50 * 1024 * 1024);

      transports.add(new WebSocketTransport(new StandardWebSocketClient(webSocketContainer)));
      WebSocketClient webSocketClient = new SockJsClient(transports);

      WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
      stompClient.setInboundMessageSizeLimit(50 * 1024 * 1024);
      stompClient.setMessageConverter(new MappingJackson2MessageConverter());
      stompClient.setTaskScheduler(new DefaultManagedTaskScheduler()); // for heartbeats

      String url = "ws://localhost:8080/stomp/";
      StompSessionHandler sessionHandler = new StompMessageHandler();
      ListenableFuture<StompSession> future = stompClient.connect(url, sessionHandler);
      StompSession stompSession = future.get();
      stompSession.send("/app/talk", "123");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public void shutdown() {
    try {
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public class StompMessageHandler extends StompSessionHandlerAdapter {

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
      super.handleFrame(headers, payload);
    }
  }
}
