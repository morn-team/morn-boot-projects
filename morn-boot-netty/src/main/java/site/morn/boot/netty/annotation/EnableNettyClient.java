package site.morn.boot.netty.annotation;

import org.springframework.context.annotation.Import;
import site.morn.boot.netty.config.NettyAutoConfiguration;
import site.morn.boot.netty.config.NettyClientAutoConfiguration;

/**
 * 开启Netty客户端
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/6
 */
@Import({NettyAutoConfiguration.class, NettyClientAutoConfiguration.class})
public @interface EnableNettyClient {

}
