package site.morn.boot.netty.annotation;

import org.springframework.context.annotation.Import;
import site.morn.boot.netty.config.NettyAutoConfiguration;
import site.morn.boot.netty.config.NettyServerAutoConfiguration;

/**
 * 开启Netty服务端
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/5
 */
@Import({NettyAutoConfiguration.class, NettyServerAutoConfiguration.class})
public @interface EnableNettyServer {

}
