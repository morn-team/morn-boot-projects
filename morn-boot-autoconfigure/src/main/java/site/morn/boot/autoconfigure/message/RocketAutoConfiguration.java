package site.morn.boot.autoconfigure.message;

import java.util.List;
import org.apache.rocketmq.client.MQAdmin;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.boot.message.BroadcastMessageHeaderResolver;
import site.morn.boot.message.rocket.RocketDestinationConverter;
import site.morn.boot.message.rocket.RocketMessageHandler;
import site.morn.boot.message.rocket.RocketMessageHeaderResolver;
import site.morn.boot.message.rocket.RocketMessagePayloadResolver;
import site.morn.boot.message.rocket.RocketMessageResultConverter;
import site.morn.boot.message.rocket.RocketResolvingOperations;
import site.morn.boot.message.rocket.RocketSendingOperations;
import site.morn.boot.message.rocket.support.SimpleRocketDestinationConverter;
import site.morn.boot.message.rocket.support.SimpleRocketMessageHandler;
import site.morn.boot.message.rocket.support.SimpleRocketMessageHeaderResolver;
import site.morn.boot.message.rocket.support.SimpleRocketMessagePayloadResolver;
import site.morn.boot.message.rocket.support.SimpleRocketMessageResultConverter;
import site.morn.boot.message.rocket.support.SimpleRocketResolvingOperations;
import site.morn.boot.message.rocket.support.SimpleRocketSendingOperations;

/**
 * Rocket自动化配置
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
@Configuration
@ConditionalOnClass({MQAdmin.class, RocketMessageHandler.class})
@ConditionalOnProperty(prefix = "rocketmq", value = "name-server", matchIfMissing = true)
public class RocketAutoConfiguration {

  /**
   * 注册发送目标转换器
   */
  @Bean
  @ConditionalOnMissingBean
  public RocketDestinationConverter rocketDestinationConverter() {
    return new SimpleRocketDestinationConverter();
  }

  /**
   * 注册注解消息处理者
   */
  @Bean
  public RocketMessageHandler rocketMessageHandler() {
    return new SimpleRocketMessageHandler();
  }

  /**
   * 注册消息头解析器
   */
  @Bean
  @ConditionalOnMissingBean
  public RocketMessageHeaderResolver rocketMessageHeaderResolver() {
    return new SimpleRocketMessageHeaderResolver();
  }

  /**
   * 注册消息结果转换器
   */
  @Bean
  @ConditionalOnMissingBean
  public RocketMessageResultConverter rocketMessageResultConverter() {
    return new SimpleRocketMessageResultConverter();
  }

  /**
   * 注册消息体解析器
   */
  @Bean
  @ConditionalOnMissingBean
  public RocketMessagePayloadResolver<Object> rocketMessagePayloadResolver() {
    return new SimpleRocketMessagePayloadResolver();
  }

  /**
   * 注册消息接收操作类
   */
  @Bean
  @ConditionalOnMissingBean
  public RocketResolvingOperations rocketResolvingOperations(RocketMessageHandler messageHandler,
      List<BroadcastMessageHeaderResolver<?>> headerResolvers) {
    return new SimpleRocketResolvingOperations(messageHandler, headerResolvers);
  }

  /**
   * 注册消息发送操作类
   */
  @Bean
  @ConditionalOnBean(RocketMQTemplate.class)
  @ConditionalOnMissingBean
  public RocketSendingOperations rocketSendingOperations(RocketMQTemplate template) {
    return new SimpleRocketSendingOperations(template);
  }
}
