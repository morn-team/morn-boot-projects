package site.morn.boot.autoconfigure.message;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import site.morn.bean.BeanAnnotationRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.boot.message.BroadcastMessage;
import site.morn.boot.message.annotation.MessageTopic;

/**
 * 消息自动化配置
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/10
 */
@Configuration
@ConditionalOnClass({Message.class, BroadcastMessage.class})
public class MessageAutoConfiguration implements BeanConfigurer {

  @Override
  public void addBeanAnnotations(BeanAnnotationRegistry registry) {
    registry.add(MessageTopic.class);
  }
}
