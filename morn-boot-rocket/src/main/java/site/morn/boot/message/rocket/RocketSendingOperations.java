package site.morn.boot.message.rocket;

import static site.morn.boot.message.rocket.RocketConstants.ROCKET;

import site.morn.boot.message.BroadcastMessage;
import site.morn.boot.message.BroadcastMessageConverter;
import site.morn.boot.message.BroadcastMessageSendingOperations;
import site.morn.util.BeanFunctionUtils;

/**
 * Rocket消息发送操作
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
public interface RocketSendingOperations extends BroadcastMessageSendingOperations {

  /**
   * 获取发送目标
   *
   * <p>topic和tag组合
   */
  @SuppressWarnings("unchecked")
  default String getDestination(BroadcastMessage<?> message) {
    return BeanFunctionUtils.convert(BroadcastMessageConverter.class, message, ROCKET);
  }
}
