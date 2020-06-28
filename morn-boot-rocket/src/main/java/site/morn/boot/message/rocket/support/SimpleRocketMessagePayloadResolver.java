package site.morn.boot.message.rocket.support;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.Assert;
import site.morn.bean.annotation.Objective;
import site.morn.boot.json.util.JsonParsers;
import site.morn.boot.message.rocket.RocketMessagePayloadResolver;

/**
 * 默认Rocket消息体解析器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
@Slf4j
@Objective
public class SimpleRocketMessagePayloadResolver implements RocketMessagePayloadResolver<Object> {

  private Type payloadType;

  @Override
  public Object convert(MessageExt source) {
    Assert.notNull(payloadType, "Message|Resolver failure:尚未获取消息体类型");
    String payloadString = new String(source.getBody(), StandardCharsets.UTF_8);
    return JsonParsers.parseObject(payloadString, payloadType);
  }

  @Override
  public void setPayloadType(Type type) {
    this.payloadType = type;
  }
}
