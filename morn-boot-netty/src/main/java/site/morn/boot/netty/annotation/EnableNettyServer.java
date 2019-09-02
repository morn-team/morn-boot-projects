package site.morn.boot.netty.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import site.morn.boot.netty.config.NettyServerConfiguration;

/**
 * 开启Netty服务端
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/5
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(NettyServerConfiguration.class)
public @interface EnableNettyServer {

}
