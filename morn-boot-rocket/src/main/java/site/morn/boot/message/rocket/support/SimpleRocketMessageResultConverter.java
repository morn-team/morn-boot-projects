package site.morn.boot.message.rocket.support;

import static site.morn.boot.message.constant.MessageConstant.MessageResultStatus.FAILURE;
import static site.morn.boot.message.constant.MessageConstant.MessageResultStatus.SUCCESS;
import static site.morn.boot.message.constant.MessageConstant.MessageResultStatus.UNKNOWN;
import static site.morn.boot.message.rocket.RocketConstants.ROCKET;

import org.apache.rocketmq.client.producer.SendResult;
import site.morn.bean.annotation.Tag;
import site.morn.boot.message.MessageResult;
import site.morn.boot.message.rocket.RocketMessageResultConverter;
import site.morn.boot.message.support.SimpleMessageResult;

/**
 * Rocket消息结果转换器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
@Tag(ROCKET)
public class SimpleRocketMessageResultConverter implements RocketMessageResultConverter {

  @Override
  public MessageResult convert(SendResult source) {
    SimpleMessageResult messageResult = new SimpleMessageResult();
    messageResult.setOriginalResult(source);
    switch (source.getSendStatus()) {
      case SEND_OK:
        messageResult.setStatus(SUCCESS);
        break;
      case SLAVE_NOT_AVAILABLE:
        messageResult.setStatus(UNKNOWN);
        break;
      default:
        messageResult.setStatus(FAILURE);
        break;
    }
    return messageResult;
  }
}
