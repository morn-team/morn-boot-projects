package site.morn.boot.netty.config;

import org.springframework.context.annotation.Configuration;
import site.morn.bean.BeanAnnotationRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.boot.netty.annotation.Inbound;
import site.morn.boot.netty.annotation.Outbound;
import site.morn.boot.netty.annotation.Terminal;

/**
 * Netty自动化配置
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/6
 */
@Configuration
public class NettyAutoConfiguration implements BeanConfigurer {

  @Override
  public void addBeanAnnotations(BeanAnnotationRegistry registry) {
    registry.add(Terminal.class);
    registry.add(Inbound.class);
    registry.add(Outbound.class);
  }
}
