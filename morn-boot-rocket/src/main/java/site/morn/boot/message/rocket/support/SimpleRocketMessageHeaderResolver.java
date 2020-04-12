package site.morn.boot.message.rocket.support;

import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;
import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;
import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static site.morn.boot.message.BroadcastMessageHeaders.TYPE;

import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.MimeType;
import site.morn.boot.message.BroadcastMessageHeaders;
import site.morn.boot.message.rocket.RocketMessageHeaderResolver;
import site.morn.boot.message.support.BroadcastMessageHeaderAccessor;

/**
 * 默认Rocket消息头解析器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public class SimpleRocketMessageHeaderResolver implements RocketMessageHeaderResolver {

  @Override
  public BroadcastMessageHeaders convert(MessageExt source) {
    BroadcastMessageHeaderAccessor headerAccessor = new BroadcastMessageHeaderAccessor();
    headerAccessor.setTopic(source.getTopic()); // 主题
    headerAccessor.setTags(source.getTags()); // 标签
    headerAccessor.setType(source.getUserProperty(TYPE)); // 类型
    String contentType = source.getUserProperty(CONTENT_TYPE); // 格式
    headerAccessor.setContentType(MimeType.valueOf(contentType));
    headerAccessor.setErrorChannelName(source.getUserProperty(ERROR_CHANNEL)); // 异常通道
    headerAccessor.setReplyChannelName(source.getUserProperty(REPLY_CHANNEL)); // 回复通道
    return headerAccessor.toMessageHeaders();
  }
}
