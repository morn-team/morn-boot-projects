package site.morn.boot.message.rocket;

import site.morn.boot.message.BroadcastMessage;
import site.morn.boot.message.BroadcastMessageConverter;

/**
 * Rocket消息目标转换器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
public interface RocketDestinationConverter extends BroadcastMessageConverter<String> {

  /**
   * 转换发送目标
   *
   * @param source 源消息
   * @return 发送目标
   */
  @Override
  String convert(BroadcastMessage<?> source);
}
