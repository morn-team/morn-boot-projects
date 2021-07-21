package site.morn.boot.netty.support;

import site.morn.bean.annotation.Target;
import site.morn.boot.netty.NettyClient;
import site.morn.boot.netty.adapter.NettyClientProducer;
import site.morn.boot.netty.adapter.TerminalChannelAdapters.TerminalChannelPoolHandler;
import site.morn.boot.netty.config.NettyClientConfig;
import site.morn.boot.netty.constant.TerminalTypeConstants;

/**
 * 远程连接客户端生产者
 *
 * @author timely-rain
 * @since 1.2.2, 2020/9/14
 */
@Target(RemoteAddressChannelPoolIdentify.class)
public class RemoteAddressClientProducer implements
    NettyClientProducer<RemoteAddressChannelPoolIdentify> {

  @Override
  public NettyClient create(RemoteAddressChannelPoolIdentify identify) {
    NettyClientConfig clientConfig = new NettyClientConfig();
    clientConfig.setServerHost(identify.getRemoteHost());
    clientConfig.setServerPort(identify.getRemotePort());
    TerminalChannelPoolHandler channelPoolHandler = new TerminalChannelPoolHandler(
        TerminalTypeConstants.CLIENT);
    return new NettyClient(clientConfig, channelPoolHandler);
  }
}

