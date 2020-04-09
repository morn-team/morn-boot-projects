package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import site.morn.bean.BeanAnnotationRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.boot.message.AnnotationBroadcastMessageHandler;
import site.morn.boot.message.annotation.MessageTopic;

/**
 * 消息自动化配置
 */
@Configuration
@ConditionalOnClass(Message.class)
public class MessageAutoConfiguration implements BeanConfigurer {

  @Override
  public void addBeanAnnotations(BeanAnnotationRegistry registry) {
    registry.add(MessageTopic.class);
  }

  /**
   * 注册注解消息处理者
   */
  @Bean
  public AnnotationBroadcastMessageHandler annotationBroadcastMessageHandler() {
    return new AnnotationBroadcastMessageHandler();
  }
}
