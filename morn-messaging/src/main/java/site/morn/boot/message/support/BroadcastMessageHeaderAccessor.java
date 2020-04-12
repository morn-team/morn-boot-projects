package site.morn.boot.message.support;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageHeaderAccessor;
import site.morn.boot.message.BroadcastMessageHeaders;

/**
 * 消息头访问者
 *
 * <p>消息头一旦生成无法修改，可使用{@link BroadcastMessageHeaderAccessor}重新生成
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/9
 */
public class BroadcastMessageHeaderAccessor extends MessageHeaderAccessor {

  public BroadcastMessageHeaderAccessor() {
    super();
  }

  public BroadcastMessageHeaderAccessor(Message<?> message) {
    super(message);
  }

  @Override
  protected BroadcastMessageHeaderAccessor createAccessor(Message<?> message) {
    return new BroadcastMessageHeaderAccessor(message);
  }

  /**
   * 设置主题
   *
   * @param topic 主题名称
   */
  public void setTopic(String topic) {
    setHeader(BroadcastMessageHeaders.TOPIC, topic);
  }

  /**
   * 设置标签
   *
   * @param tags 标签数组
   */
  public void setTags(String... tags) {
    setHeader(BroadcastMessageHeaders.TAG, tags);
  }

  /**
   * 设置类型
   *
   * @param type 类型名称
   */
  public void setType(String type) {
    setHeader(BroadcastMessageHeaders.TYPE, type);
  }

  @Override
  public BroadcastMessageHeaders toMessageHeaders() {
    return new BroadcastMessageHeaders(getMessageHeaders());
  }
}
