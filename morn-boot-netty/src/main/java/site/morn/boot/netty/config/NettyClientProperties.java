package site.morn.boot.netty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Netty客户端配置项
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
 */
@ConfigurationProperties("morn.netty.client")
public class NettyClientProperties extends NettyClientConfig {

}
