package site.morn.boot.message.support;

import static site.morn.boot.message.constant.MessageConstant.Errors.MESSAGE_RESOLVE_FAILURE;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.util.StringUtils;
import site.morn.bean.AnnotationIdentifyCase;
import site.morn.bean.AnnotationIdentifyCase.AnnotationIdentifyCaseBuilder;
import site.morn.bean.BeanCaches;
import site.morn.bean.BeanFunctions;
import site.morn.bean.FunctionHolder;
import site.morn.bean.Tags;
import site.morn.boot.message.BroadcastMessage;
import site.morn.boot.message.BroadcastMessageHeaders;
import site.morn.boot.message.annotation.MessageTopic;
import site.morn.exception.ApplicationMessages;

/**
 * 注解消息处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/9
 */
@Slf4j
public abstract class AbstractAnnotationBroadcastMessageHandler<T> implements
    AnnotationBroadcastMessageHandler<T> {

  /**
   * 类型转换服务
   */
  private ConfigurableConversionService conversionService;

  @Override
  public void handleMessage(T message) {
    BroadcastMessageHeaders headers = conversionService
        .convert(message, BroadcastMessageHeaders.class);
    String messageTopic = headers.getTopic(); // 消息主题
    String messageType = headers.getType(); // 消息类型
    FunctionHolder functionHolder = getHandleFunctionHolder(messageTopic, messageType);
    if (Objects.isNull(functionHolder)) {
      log.error("Message|Handle failure:Payload method no found|Topic:{}, Type:{}", messageTopic,
          messageType);
      return;
    }
    Object payload = resolvePayload(message, functionHolder);
    if (Objects.isNull(payload)) {
      throw ApplicationMessages
          .translateException(MESSAGE_RESOLVE_FAILURE, messageTopic, messageType);
    }
    BroadcastMessage<Object> broadcastMessage = new SimpleBroadcastMessage<>(payload, headers);
    BeanFunctions.call(functionHolder, payload, headers, broadcastMessage);
  }

  @Override
  public void handleMessage(BroadcastMessage<?> message) {
    BroadcastMessageHeaders headers = message.getHeaders();
    String messageTopic = headers.getTopic(); // 消息主题
    String messageType = headers.getType(); // 消息类型
    FunctionHolder functionHolder = getHandleFunctionHolder(messageTopic, messageType);
    if (Objects.isNull(functionHolder)) {
      log.error("Message|Handle failure:Payload method no found|Topic:{}, Type:{}", messageTopic,
          messageType);
      return;
    }
    BeanFunctions.call(functionHolder, message.getPayload(), headers, message);
  }

  /**
   * 获取消息处理函数
   *
   * @param messageTopic 消息主题
   * @param messageType 消息类型
   * @return 消息处理函数
   */
  @Override
  public FunctionHolder getHandleFunctionHolder(String messageTopic, String messageType) {
    // 构建消息处理类的检索标识
    String[] tags = Tags.from(MessageTopic.class, messageTopic).toArray();
    AnnotationIdentifyCase beanIdentify = AnnotationIdentifyCase.builder().tags(tags)
        .build(); // 使用MessageTopic检索处理类
    // 构建消息处理函数的检索标识
    AnnotationIdentifyCaseBuilder functionBuilder = AnnotationIdentifyCase.builder();
    if (!StringUtils.isEmpty(messageType)) {
      functionBuilder.name(messageType); // 使用MessageType检索处理函数
    }
    AnnotationIdentifyCase functionIdentify = functionBuilder.build();
    List<FunctionHolder> functions = BeanCaches.functions(beanIdentify, functionIdentify);
    if (functions.isEmpty()) {
      log.warn("Message|No function：{}.{}", messageTopic,
          Optional.ofNullable(messageType).orElse("*"));
      return null;
    }
    if (functions.size() > 1) {
      log.warn("Message|Multipart function：{}", messageTopic);
      return null;
    }
    return functions.get(0);
  }

  @Override
  public void setConversionService(ConfigurableConversionService conversionService) {
    this.conversionService = conversionService;
  }

  /**
   * 解析消息体
   *
   * @param message 原消息
   * @param functionHolder 消息处理函数
   * @return 消息体
   */
  protected Object resolvePayload(T message, FunctionHolder functionHolder) {
    @SuppressWarnings("unchecked")
    AnnotationBroadcastMessagePayloadResolver<T, Object> messageResolver = BeanCaches
        .bean(AnnotationBroadcastMessagePayloadResolver.class);
    Type payloadType = getPayloadType(functionHolder);
    messageResolver.setPayloadType(payloadType);
    return messageResolver.convert(message);
  }
}