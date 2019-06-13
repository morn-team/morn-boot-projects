package site.morn.boot.netty.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import site.morn.boot.netty.config.NettyAutoConfiguration;
import site.morn.boot.netty.config.NettyServerAutoConfiguration;

/**
 * 开启Netty服务端
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/5
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({NettyAutoConfiguration.class, NettyServerAutoConfiguration.class})
public @interface EnableNettyServer {

}
