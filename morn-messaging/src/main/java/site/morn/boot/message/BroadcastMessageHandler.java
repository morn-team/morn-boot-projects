package site.morn.boot.message;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import site.morn.util.GenericUtils;

/**
 * 消息处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/9
 */
public interface BroadcastMessageHandler extends MessageHandler {

  /**
   * 处理消息
   *
   * @param message 消息
   */
  void handleMessage(BroadcastMessage<?> message);

  @Override
  default void handleMessage(Message<?> message) {
    if (message instanceof BroadcastMessage) {
      BroadcastMessage<?> broadcastMessage = GenericUtils.castFrom(message);
      handleMessage(broadcastMessage);
    }
  }
}
