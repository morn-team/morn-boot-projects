package site.morn.boot.message.rocket;

import org.apache.rocketmq.common.message.MessageExt;
import site.morn.boot.message.BroadcastMessageHeaderResolver;

/**
 * Rocket消息头解析器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface RocketMessageHeaderResolver extends BroadcastMessageHeaderResolver<MessageExt> {

}
