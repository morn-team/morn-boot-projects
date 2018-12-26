package site.morn.boot.websocket.client;

import lombok.extern.slf4j.Slf4j;
import org.projectodd.stilts.stomp.StompMessage;
import org.projectodd.stilts.stomp.Subscription.AckMode;
import org.projectodd.stilts.stomp.client.ClientSubscription;
import org.projectodd.stilts.stomp.client.MessageHandler;
import org.projectodd.stilts.stomp.client.StompClient;

/**
 * site.morn.boot.websocket.client
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/26
 */
@Slf4j
public class TestStompClient {

  private StompClient client;
  private ClientSubscription subscription1;
  private ClientSubscription subscription2;


  public void startup() {
    try {
      client = new StompClient("stomp+ws://localhost:8080/stomp");
      client.connect();

      subscription1 =
          client.subscribe("/topic/talk")
              .withMessageHandler(new StompMessageHandler())
              .withAckMode(AckMode.CLIENT_INDIVIDUAL)
              .start();

      subscription2 =
          client.subscribe("/topic/talk")
              .withMessageHandler(new StompMessageHandler())
              .withAckMode(AckMode.AUTO)
              .start();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public void shutdown() {
    try {
      subscription1.unsubscribe();
      subscription2.unsubscribe();
      client.disconnect();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public class StompMessageHandler implements MessageHandler {

    @Override
    public void handle(StompMessage message) {
      log.info(message.getContent().toString());
    }
  }
}
