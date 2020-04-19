package site.morn.boot.message.rocket.support;

import java.util.List;
import org.apache.rocketmq.common.message.MessageExt;
import site.morn.boot.message.BroadcastMessageHeaderResolver;
import site.morn.boot.message.rocket.RocketResolvingOperations;
import site.morn.boot.message.support.AbstractBroadcastMessageResolvingOperations;
import site.morn.boot.message.support.AnnotationBroadcastMessageHandler;

/**
 * 默认Rocket消息解析操作类
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public class SimpleRocketResolvingOperations extends
    AbstractBroadcastMessageResolvingOperations<MessageExt> implements RocketResolvingOperations {

  public SimpleRocketResolvingOperations(
      AnnotationBroadcastMessageHandler<MessageExt> messageHandler,
      List<BroadcastMessageHeaderResolver<?>> headerResolvers) {
    super(messageHandler, headerResolvers);
  }

  @Override
  public boolean syncResolve(MessageExt message) {
    getMessageHandler().handleMessage(message);
    return true;
  }
}
