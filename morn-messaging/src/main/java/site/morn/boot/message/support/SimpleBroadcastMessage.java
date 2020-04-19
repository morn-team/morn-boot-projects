package site.morn.boot.message.support;

import java.util.Map;
import lombok.Getter;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;
import site.morn.boot.message.BroadcastMessage;
import site.morn.boot.message.BroadcastMessageHeaders;

/**
 * 默认消息
 *
 * @param <P> 消息体类型
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
@Getter
public class SimpleBroadcastMessage<P> implements BroadcastMessage<P> {

  private static final long serialVersionUID = 4268801052358035098L;

  /**
   * 消息体
   */
  private final P payload;

  /**
   * 消息头
   */
  private final BroadcastMessageHeaders headers;

  /**
   * Create a new message with the given payload.
   *
   * @param payload the message payload (never {@code null})
   */
  public SimpleBroadcastMessage(P payload) {
    this(payload, new MessageHeaders(null));
  }

  /**
   * Create a new message with the given payload and headers. The content of the given header map is
   * copied.
   *
   * @param payload the message payload (never {@code null})
   * @param headers message headers to use for initialization
   */
  public SimpleBroadcastMessage(P payload, Map<String, Object> headers) {
    this(payload, new BroadcastMessageHeaders(headers));
  }

  /**
   * A constructor with the {@link BroadcastMessageHeaders} instance to use.
   * <p><strong>Note:</strong> the given {@code MessageHeaders} instance is used
   * directly in the new message, i.e. it is not copied.
   *
   * @param payload the message payload (never {@code null})
   * @param headers message headers
   */
  public SimpleBroadcastMessage(P payload, MessageHeaders headers) {
    this(payload, new BroadcastMessageHeaders(headers));
  }

  public SimpleBroadcastMessage(P payload, BroadcastMessageHeaders headers) {
    Assert.notNull(payload, "Payload must not be null");
    Assert.notNull(headers, "MessageHeaders must not be null");
    this.payload = payload;
    this.headers = headers;
  }
}
