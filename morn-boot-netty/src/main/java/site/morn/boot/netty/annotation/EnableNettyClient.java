package site.morn.boot.netty.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import site.morn.boot.netty.config.NettyClientConfiguration;

/**
 * 开启Netty客户端
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/6
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(NettyClientConfiguration.class)
public @interface EnableNettyClient {

}
