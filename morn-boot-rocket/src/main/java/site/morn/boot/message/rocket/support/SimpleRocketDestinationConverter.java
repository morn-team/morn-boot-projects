package site.morn.boot.message.rocket.support;

import static site.morn.boot.message.rocket.RocketConstants.ROCKET;

import site.morn.bean.annotation.Tag;
import site.morn.boot.message.BroadcastMessage;
import site.morn.boot.message.BroadcastMessageHeaders;
import site.morn.boot.message.rocket.RocketDestinationConverter;

/**
 * 默认Rocket消息目标转换器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
@Tag(ROCKET)
public class SimpleRocketDestinationConverter implements RocketDestinationConverter {

  @Override
  public String convert(BroadcastMessage<?> source) {
    BroadcastMessageHeaders headers = source.getHeaders();
    return headers.getTopic();
  }
}
