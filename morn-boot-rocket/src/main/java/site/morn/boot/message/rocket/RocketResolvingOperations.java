package site.morn.boot.message.rocket;

import org.apache.rocketmq.common.message.MessageExt;
import site.morn.boot.message.BroadcastMessageResolvingOperations;

/**
 * Rocket消息解析操作
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface RocketResolvingOperations extends BroadcastMessageResolvingOperations<MessageExt> {

}
