package site.morn.boot.message.rocket;

import org.apache.rocketmq.common.message.MessageExt;
import site.morn.boot.message.support.AnnotationBroadcastMessagePayloadResolver;

/**
 * Rocket消息体解析器
 *
 * @param <P> 消息体类型
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface RocketMessagePayloadResolver<P> extends
    AnnotationBroadcastMessagePayloadResolver<MessageExt, P> {

  @Override
  P convert(MessageExt source);
}
