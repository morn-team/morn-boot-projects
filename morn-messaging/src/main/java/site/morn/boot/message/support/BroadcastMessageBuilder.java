package site.morn.boot.message.support;

import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.Assert;
import site.morn.boot.message.BroadcastMessage;
import site.morn.util.GenericUtils;

/**
 * 消息构建器
 *
 * @param <T> 消息体类型
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
public class BroadcastMessageBuilder<T> {

  /**
   * 消息体
   */
  private final T payload;

  /**
   * 消息头
   */
  private BroadcastMessageHeaderAccessor headerAccessor;

  /**
   * 原始消息
   */
  @Nullable
  private final Message<T> originalMessage;

  private BroadcastMessageBuilder(Message<T> originalMessage) {
    Assert.notNull(originalMessage, "Message must not be null");
    this.payload = originalMessage.getPayload();
    this.originalMessage = originalMessage;
    this.headerAccessor = new BroadcastMessageHeaderAccessor(originalMessage);
  }

  private BroadcastMessageBuilder(T payload, BroadcastMessageHeaderAccessor accessor) {
    Assert.notNull(payload, "Payload must not be null");
    Assert.notNull(accessor, "MessageHeaderAccessor must not be null");
    this.payload = payload;
    this.originalMessage = null;
    this.headerAccessor = accessor;
  }


  /**
   * Set the message headers to use by providing a {@code MessageHeaderAccessor}.
   *
   * @param accessor the headers to use
   */
  public BroadcastMessageBuilder<T> setHeaders(BroadcastMessageHeaderAccessor accessor) {
    Assert.notNull(accessor, "BroadcastMessageHeaderAccessor must not be null");
    this.headerAccessor = accessor;
    return this;
  }

  /**
   * Set the value for the given header name. If the provided value is {@code null}, the header will
   * be removed.
   */
  public BroadcastMessageBuilder<T> setHeader(String headerName, @Nullable Object headerValue) {
    this.headerAccessor.setHeader(headerName, headerValue);
    return this;
  }

  /**
   * Set the value for the given header name only if the header name is not already associated with
   * a value.
   */
  public BroadcastMessageBuilder<T> setHeaderIfAbsent(String headerName, Object headerValue) {
    this.headerAccessor.setHeaderIfAbsent(headerName, headerValue);
    return this;
  }

  /**
   * Removes all headers provided via array of 'headerPatterns'. As the name suggests the array may
   * contain simple matching patterns for header names. Supported pattern styles are: "xxx*",
   * "*xxx", "*xxx*" and "xxx*yyy".
   */
  public BroadcastMessageBuilder<T> removeHeaders(String... headerPatterns) {
    this.headerAccessor.removeHeaders(headerPatterns);
    return this;
  }

  /**
   * Remove the value for the given header name.
   */
  public BroadcastMessageBuilder<T> removeHeader(String headerName) {
    this.headerAccessor.removeHeader(headerName);
    return this;
  }

  /**
   * Copy the name-value pairs from the provided Map. This operation will overwrite any existing
   * values. Use { {@link #copyHeadersIfAbsent(Map)} to avoid overwriting values. Note that the 'id'
   * and 'timestamp' header values will never be overwritten.
   */
  public BroadcastMessageBuilder<T> copyHeaders(@Nullable Map<String, ?> headersToCopy) {
    this.headerAccessor.copyHeaders(headersToCopy);
    return this;
  }

  /**
   * Copy the name-value pairs from the provided Map. This operation will <em>not</em> overwrite any
   * existing values.
   */
  public BroadcastMessageBuilder<T> copyHeadersIfAbsent(@Nullable Map<String, ?> headersToCopy) {
    this.headerAccessor.copyHeadersIfAbsent(headersToCopy);
    return this;
  }

  public BroadcastMessageBuilder<T> setTopic(String topic) {
    this.headerAccessor.setTopic(topic);
    return this;
  }

  public BroadcastMessageBuilder<T> setTag(String... tags) {
    this.headerAccessor.setTags(tags);
    return this;
  }

  public BroadcastMessageBuilder<T> setType(String type) {
    this.headerAccessor.setType(type);
    return this;
  }

  public BroadcastMessageBuilder<T> setReplyChannel(MessageChannel replyChannel) {
    this.headerAccessor.setReplyChannel(replyChannel);
    return this;
  }

  public BroadcastMessageBuilder<T> setReplyChannelName(String replyChannelName) {
    this.headerAccessor.setReplyChannelName(replyChannelName);
    return this;
  }

  public BroadcastMessageBuilder<T> setErrorChannel(MessageChannel errorChannel) {
    this.headerAccessor.setErrorChannel(errorChannel);
    return this;
  }

  public BroadcastMessageBuilder<T> setErrorChannelName(String errorChannelName) {
    this.headerAccessor.setErrorChannelName(errorChannelName);
    return this;
  }

  public BroadcastMessage<T> build() {
    if (this.originalMessage != null && !this.headerAccessor.isModified()) {
      BroadcastMessage<T> message = BroadcastMessageBuilder.fromMessage(originalMessage).build();
      return GenericUtils.castFrom(message);
    }
    MessageHeaders headersToUse = this.headerAccessor.toMessageHeaders();
    return new SimpleBroadcastMessage<>(this.payload, headersToUse);
  }


  /**
   * Create a builder for a new {@link Message} instance pre-populated with all of the headers
   * copied from the provided message. The payload of the provided Message will also be used as the
   * payload for the new message.
   *
   * @param message the Message from which the payload and all headers will be copied
   */
  public static <T> BroadcastMessageBuilder<T> fromMessage(Message<T> message) {
    return new BroadcastMessageBuilder<>(message);
  }

  /**
   * Create a new builder for a message with the given payload.
   *
   * @param payload the payload
   */
  public static <T> BroadcastMessageBuilder<T> withPayload(T payload) {
    return new BroadcastMessageBuilder<>(payload, new BroadcastMessageHeaderAccessor());
  }

  /**
   * A shortcut factory method for creating a message with the given payload and {@code
   * MessageHeaders}.
   * <p><strong>Note:</strong> the given {@code MessageHeaders} instance is used
   * directly in the new message, i.e. it is not copied.
   *
   * @param payload the payload to use (never {@code null})
   * @param messageHeaders the headers to use (never {@code null})
   * @return the created message
   * @since 4.1
   */
  @SuppressWarnings("unchecked")
  public static <T> Message<T> createMessage(@Nullable T payload, MessageHeaders messageHeaders) {
    Assert.notNull(payload, "Payload must not be null");
    Assert.notNull(messageHeaders, "MessageHeaders must not be null");
    if (payload instanceof Throwable) {
      return (Message<T>) new ErrorMessage((Throwable) payload, messageHeaders);
    } else {
      return new GenericMessage<>(payload, messageHeaders);
    }
  }
}
