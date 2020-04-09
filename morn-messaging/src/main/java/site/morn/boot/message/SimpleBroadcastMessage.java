package site.morn.boot.message;

import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;

@Getter
@Setter
public class SimpleBroadcastMessage<T extends Serializable> implements BroadcastMessage<T>,
    Serializable {

  private static final long serialVersionUID = 4268801052358035098L;


  /**
   * 消息体
   */
  private final T payload;

  /**
   * 消息头
   */
  private final BroadcastMessageHeaders headers;

  /**
   * Create a new message with the given payload.
   *
   * @param payload the message payload (never {@code null})
   */
  public SimpleBroadcastMessage(T payload) {
    this(payload, new MessageHeaders(null));
  }

  /**
   * Create a new message with the given payload and headers. The content of the given header map is
   * copied.
   *
   * @param payload the message payload (never {@code null})
   * @param headers message headers to use for initialization
   */
  public SimpleBroadcastMessage(T payload, Map<String, Object> headers) {
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
  public SimpleBroadcastMessage(T payload, BroadcastMessageHeaders headers) {
    Assert.notNull(payload, "Payload must not be null");
    Assert.notNull(headers, "MessageHeaders must not be null");
    this.payload = payload;
    this.headers = headers;
  }
}
