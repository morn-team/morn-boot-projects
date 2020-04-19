package site.morn.boot.message.rocket;

import org.apache.rocketmq.common.message.MessageExt;
import site.morn.boot.message.support.AnnotationBroadcastMessageHandler;

/**
 * Rocket消息处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface RocketMessageHandler extends AnnotationBroadcastMessageHandler<MessageExt> {

}
