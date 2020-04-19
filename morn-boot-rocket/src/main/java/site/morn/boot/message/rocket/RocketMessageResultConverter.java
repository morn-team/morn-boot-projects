package site.morn.boot.message.rocket;

import org.apache.rocketmq.client.producer.SendResult;
import site.morn.boot.message.MessageResult;
import site.morn.boot.message.MessageResultConverter;

/**
 * Rocket消息结果转换器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
public interface RocketMessageResultConverter extends MessageResultConverter<SendResult> {

  /**
   * 转换消息结果
   *
   * @param source 源结果
   * @return 标准结果
   */
  @Override
  MessageResult convert(SendResult source);
}
