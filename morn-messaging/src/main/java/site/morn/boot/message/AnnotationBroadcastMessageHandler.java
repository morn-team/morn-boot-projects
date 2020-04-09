package site.morn.boot.message;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import site.morn.bean.AnnotationIdentifyCase;
import site.morn.bean.AnnotationIdentifyCase.AnnotationIdentifyCaseBuilder;
import site.morn.bean.BeanCaches;
import site.morn.bean.BeanFunctions;
import site.morn.bean.FunctionHolder;
import site.morn.bean.Tags;
import site.morn.boot.message.annotation.MessageTopic;

/**
 * 注解消息处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/9
 */
@Slf4j
public class AnnotationBroadcastMessageHandler implements BroadcastMessageHandler {

  @Override
  public void handleMessage(BroadcastMessage<?> message) {
    BroadcastMessageHeaders headers = message.getHeaders();
    String messageTopic = headers.getTopic();
    String messageType = headers.getType();
    // 调用消息主题对应的处理函数
    String[] tags = Tags.from(MessageTopic.class, messageTopic).toArray();
    AnnotationIdentifyCase beanIdentify = AnnotationIdentifyCase.builder().tags(tags)
        .build(); // 使用MessageTopic检索处理类
    AnnotationIdentifyCaseBuilder functionBuilder = AnnotationIdentifyCase.builder();
    if (!StringUtils.isEmpty(messageType)) {
      functionBuilder.name(messageType); // 使用MessageType检索处理函数
    }
    AnnotationIdentifyCase functionIdentify = functionBuilder.build();
    List<FunctionHolder> functions = BeanCaches.functions(beanIdentify, functionIdentify);
    if (functions.isEmpty()) {
      log.warn("Message|No function：{}.{}", messageTopic,
          Optional.ofNullable(messageType).orElse("*"));
    }
    if (functions.size() > 1) {
      log.warn("Message|Multipart function：{}", messageTopic);
    }
    BeanFunctions.call(functions, message.getPayload(), headers, message);
  }
}