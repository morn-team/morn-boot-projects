package site.morn.boot.message.support;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.Assert;
import site.morn.bean.FunctionHolder;
import site.morn.boot.message.BroadcastMessageHandler;

/**
 * 注解消息处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/9
 */
public interface AnnotationBroadcastMessageHandler<T> extends BroadcastMessageHandler {

  /**
   * 处理消息
   *
   * @param message 消息
   */
  void handleMessage(T message);

  /**
   * 获取消息处理函数
   *
   * @param messageTopic 消息主题
   * @param messageType 消息类型
   * @return 消息处理函数
   */
  FunctionHolder getHandleFunctionHolder(String messageTopic, String messageType);

  /**
   * 获取消息体类型
   *
   * @param functionHolder 消息处理函数
   * @return 消息体类型
   */
  default Type getPayloadType(FunctionHolder functionHolder) {
    Method method = functionHolder.getMethod();
    Parameter payloadParameter = Arrays.stream(method.getParameters())
        .filter(parameter -> Objects.nonNull(parameter.getAnnotation(Payload.class))).findAny()
        .orElse(null);
    Assert
        .notNull(payloadParameter, "Payload parameter no found. Please check your payload method.");
    return payloadParameter.getParameterizedType();
  }

  /**
   * 设置类型转换服务
   *
   * @param conversionService 类型转换服务
   */
  void setConversionService(ConfigurableConversionService conversionService);
}
