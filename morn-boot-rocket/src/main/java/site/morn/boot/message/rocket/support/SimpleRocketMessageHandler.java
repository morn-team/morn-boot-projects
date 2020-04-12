package site.morn.boot.message.rocket.support;

import org.apache.rocketmq.common.message.MessageExt;
import site.morn.boot.message.rocket.RocketMessageHandler;
import site.morn.boot.message.support.AbstractAnnotationBroadcastMessageHandler;

/**
 * 默认Rocket消息处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public class SimpleRocketMessageHandler extends
    AbstractAnnotationBroadcastMessageHandler<MessageExt> implements RocketMessageHandler {

}
