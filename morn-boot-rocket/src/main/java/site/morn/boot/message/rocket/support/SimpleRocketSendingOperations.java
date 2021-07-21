package site.morn.boot.message.rocket.support;

import static site.morn.boot.message.rocket.RocketConstants.ROCKET;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import site.morn.boot.message.BroadcastMessage;
import site.morn.boot.message.MessageResult;
import site.morn.boot.message.MessageResultConverter;
import site.morn.boot.message.rocket.RocketSendingOperations;
import site.morn.util.BeanFunctionUtils;

/**
 * 默认Rocket消息发送操作类
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
public class SimpleRocketSendingOperations implements RocketSendingOperations {

  /**
   * Rocket原生操作类
   */
  private final RocketMQTemplate template;

  public SimpleRocketSendingOperations(RocketMQTemplate template) {
    this.template = template;
  }

  @Override
  @SuppressWarnings("unchecked")
  public MessageResult syncSend(BroadcastMessage<?> message) {
    String destination = getDestination(message);
    SendResult sendResult = template.syncSend(destination, message);
    return BeanFunctionUtils.convert(MessageResultConverter.class, sendResult, ROCKET);
  }
}
