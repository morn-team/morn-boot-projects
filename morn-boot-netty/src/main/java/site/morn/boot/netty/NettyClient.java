package site.morn.boot.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.concurrent.ListenableFuture;
import site.morn.boot.netty.config.NettyClientProperties;
import site.morn.boot.netty.constant.TerminalType;
import site.morn.task.ListenableFutureDispatcher;

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
  private volatile Channel channel;

  public NettyClient(NettyClientProperties properties) {
    this.properties = properties;
    this.bootstrap = new Bootstrap();
    this.group = new NioEventLoopGroup();
  }

  /**
   * 销毁
   */
  @PreDestroy
  public void close() {
    group.shutdownGracefully();
  }

  /**
   * 连接
   */
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    bootstrap
        .group(group)
        .channel(NioSocketChannel.class)
        .handler(new NettyChannelInitializer(TerminalType.CLIENT))
        .connect(properties.getServerHost(), properties.getServerPort());

    if (properties.isAutoStart()) {
      connect();
    }
  }

  /**
   * 连接服务端
   */
  public ListenableFuture<Channel> connect() {
    return ListenableFutureDispatcher.submit(this::connectSync);
  }

  /**
   * 同步连接服务端
   *
   * @return 消息通道
   */
  private Channel connectSync() {
    Channel c = null;
    try {
      ChannelFuture channelFuture = bootstrap
          .connect(properties.getServerHost(), properties.getServerPort()).sync();
      c = channelFuture.channel();
      if (channelFuture.isSuccess()) {
        this.channel = c;
        log.info("Netty|连接成功：{}", c.id());
      } else {
        log.info("Netty|重新连接：{}", c.id());
        final EventLoop loop = c.eventLoop();
        loop.schedule(this::connect, properties.getConnectDelay(), TimeUnit.SECONDS).sync();
      }
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    }
    return c;
  }
}
