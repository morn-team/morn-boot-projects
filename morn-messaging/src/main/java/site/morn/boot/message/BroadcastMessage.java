package site.morn.boot.message;

import org.springframework.messaging.Message;

/**
 * 广播消息
 *
 * @param <T> 消息体类型
 * @author timely-rain
 * @since 1.2.1, 2020/4/8
 */
public interface BroadcastMessage<T> extends Message<T> {

  /**
   * 获取消息头
   *
   * @return 消息头
   */
  @Override
  BroadcastMessageHeaders getHeaders();
}
