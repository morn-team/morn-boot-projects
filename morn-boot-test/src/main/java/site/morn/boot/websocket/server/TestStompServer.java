package site.morn.boot.websocket.server;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import site.morn.rest.RestBuilders;
import site.morn.rest.RestMessage;

/**
 * site.morn.boot.websocket.server
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/26
 */
@Slf4j
@Controller
public class TestStompServer {

  @MessageMapping("/talk")
  public RestMessage handleShout(String message) {
    log.info("Received message:" + message);
    return RestBuilders.successMessage();
  }

  @SubscribeMapping("/subscribe")
  public RestMessage handleSubscribe() {
    Map<String, Object> data = new HashMap<>();
    data.put("message", "test123");
    return RestBuilders.successMessage(data);
  }
}
