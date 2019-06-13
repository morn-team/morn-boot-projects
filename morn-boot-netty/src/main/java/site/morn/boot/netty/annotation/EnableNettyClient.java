package site.morn.boot.netty.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import site.morn.boot.netty.config.NettyAutoConfiguration;
import site.morn.boot.netty.config.NettyClientAutoConfiguration;

/**
 * 开启Netty客户端
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/6
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({NettyAutoConfiguration.class, NettyClientAutoConfiguration.class})
public @interface EnableNettyClient {

}
