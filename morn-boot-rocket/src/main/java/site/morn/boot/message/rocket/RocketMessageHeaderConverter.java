package site.morn.boot.message.rocket;

import org.apache.rocketmq.common.message.MessageClientExt;
import site.morn.boot.message.BroadcastMessageHeaderConverter;

/**
 * Rocket消息头转换器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface RocketMessageHeaderConverter extends
    BroadcastMessageHeaderConverter<MessageClientExt> {

}
