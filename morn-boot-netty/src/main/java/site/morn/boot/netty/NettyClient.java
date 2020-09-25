package site.morn.boot.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import site.morn.boot.netty.config.NettyClientConfig;

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
  private final NettyClientConfig config;

  /**
   * 连接池处理者
   */
  private final ChannelPoolHandler channelPoolHandler;

  /**
   * 创建bootstrap
   */
  private Bootstrap bootstrap;

  /**
   * Main
   */
  private EventLoopGroup group;

  /**
   * 通道池
   */
  private FixedChannelPool channelPool;

  public NettyClient(NettyClientConfig config, ChannelPoolHandler channelPoolHandler) {
    Assert.notNull(config, "客户端配置不能为空");
    Assert.notNull(channelPoolHandler, "连接池处理者不能为空");
    this.config = config;
    this.channelPoolHandler = channelPoolHandler;
    this.bootstrap = new Bootstrap();
  }

  /**
   * 销毁
   */
  public void close() {
    group.shutdownGracefully();
  }

  /**
   * 初始化
   */
  public void init() {
    init(new NioEventLoopGroup());
  }

  /**
   * 初始化
   */
  public void init(EventLoopGroup group) {
    this.group = group;
    bootstrap
        .group(group)
        .channel(NioSocketChannel.class)
        .remoteAddress(config.getServerHost(), config.getServerPort());
    channelPool = new FixedChannelPool(
        bootstrap,
        channelPoolHandler,
        config.getMaxConnections());
  }
}
