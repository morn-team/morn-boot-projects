package site.morn.boot.message.rocket.support;

import static site.morn.boot.message.constant.MessageConstant.Errors.MESSAGE_RESOLVE_FAILURE;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.Assert;
import site.morn.boot.message.rocket.RocketMessagePayloadResolver;
import site.morn.exception.ApplicationMessages;

/**
 * 默认Rocket消息体解析器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
@Slf4j
public class SimpleRocketMessagePayloadResolver implements RocketMessagePayloadResolver<Object> {

  private final ObjectMapper objectMapper;

  private Type payloadType;

  public SimpleRocketMessagePayloadResolver(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Object convert(MessageExt source) {
    Assert.notNull(payloadType, "Message|Resolver failure:尚未获取消息体类型");
    String payloadString = new String(source.getBody(), StandardCharsets.UTF_8);
    JavaType javaType = objectMapper.getTypeFactory().constructType(payloadType);
    try {
      return objectMapper.readValue(payloadString, javaType);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw ApplicationMessages.translateException(MESSAGE_RESOLVE_FAILURE);
    }
  }

  @Override
  public void setPayloadType(Type type) {
    this.payloadType = type;
  }
}
