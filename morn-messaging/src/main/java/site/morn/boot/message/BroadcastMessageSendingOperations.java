package site.morn.boot.message;

import java.util.concurrent.CompletableFuture;
import site.morn.task.TaskExecutors;

/**
 * 消息发送操作
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
public interface BroadcastMessageSendingOperations {

  /**
   * 异步发送消息
   *
   * @param message 消息
   * @return 发送结果
   */
  default CompletableFuture<MessageResult> asyncSend(BroadcastMessage<?> message) {
    return TaskExecutors.submit(() -> syncSend(message));
  }

  /**
   * 同步发送消息
   *
   * @param message 消息
   * @return 发送结果
   */
  MessageResult syncSend(BroadcastMessage<?> message);
}
