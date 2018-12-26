package site.morn.boot.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * site.morn.boot.websocket.config
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/26
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

  public WebSocketConfig() {
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
    stompEndpointRegistry.addEndpoint("/stomp").setAllowedOrigins("*").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    //表明在topic、queue、users这三个域上可以向客户端发消息。
    registry.enableSimpleBroker("/topic", "/queue", "/users");
    //客户端向服务端发起请求时，需要以/app为前缀。
    registry.setApplicationDestinationPrefixes("/app");
    //给指定用户发送一对一的消息前缀是/users/。
    registry.setUserDestinationPrefix("/users/");
  }
}
