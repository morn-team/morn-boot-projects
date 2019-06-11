package site.morn.boot.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import site.morn.bean.BeanCaches;
import site.morn.bean.Tags;
import site.morn.boot.netty.annotation.Inbound;
import site.morn.boot.netty.annotation.Terminal;
import site.morn.boot.netty.config.NettyClientProperties;
import site.morn.boot.netty.constant.BoundType;
import site.morn.boot.netty.constant.TerminalType;

/**
 * Netty客户端
 *
 * @author timely-rain
 */
@Slf4j
@Getter
@Setter
public class NettyClient {

  /**
   * Netty配置属性
   */
  private final NettyClientProperties properties;

  /**
   * 创建bootstrap
   */
  private Bootstrap bootstrap;

  /**
   * Main
   */
  private EventLoopGroup group;

  /**
   * 通道
   */
  private Channel channel;

  public NettyClient(NettyClientProperties properties) {
    this.properties = properties;
    this.bootstrap = new Bootstrap();
    this.group = new NioEventLoopGroup();
  }

  /**
   * 连接
   */
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    ChannelInitializer<Channel> channelInitializer = new ChannelInitializer<Channel>() {
      @Override
      protected void initChannel(Channel ch) {
        String[] tags = Tags.from(Terminal.class, TerminalType.CLIENT)
            .add(Inbound.class, BoundType.AUTO).toArray();
        List<ChannelInboundHandler> handlers = BeanCaches
            .tagBeans(ChannelInboundHandler.class, tags);
        ch.pipeline().addLast(new StringEncoder()).addLast(handlers.toArray(new ChannelHandler[0]));
      }
    };

    bootstrap.group(group)
        .channel(NioSocketChannel.class)
        .handler(channelInitializer)
        .connect(properties.getServerHost(), properties.getServerPort());

    if (properties.isAutoStart()) {
      connect();
    }
  }

  /**
   * 连接服务端
   */
  public ChannelFuture connect() {
    ChannelFuture channelFuture = bootstrap
        .connect(properties.getServerHost(), properties.getServerPort());
    ChannelFutureListener listener = this::reconnect;
    return channelFuture.addListener(listener);
  }

  /**
   * 重连
   *
   * @param channelFuture 异步通道
   */
  private void reconnect(ChannelFuture channelFuture) {
    Channel c = channelFuture.channel();
    if (!channelFuture.isSuccess()) {
      log.info("Netty|重新连接...");
      final EventLoop loop = c.eventLoop();
      loop.schedule((Runnable) this::connect, properties.getConnectDelay(), TimeUnit.SECONDS);
    } else {
      this.channel = c;
      log.info("Netty|连接成功...");
    }
  }

}
