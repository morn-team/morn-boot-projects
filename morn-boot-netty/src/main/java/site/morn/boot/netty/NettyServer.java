package site.morn.boot.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import site.morn.bean.BeanCaches;
import site.morn.bean.Tags;
import site.morn.boot.netty.annotation.Inbound;
import site.morn.boot.netty.annotation.Terminal;
import site.morn.boot.netty.config.NettyServerProperties;
import site.morn.boot.netty.constant.BoundType;
import site.morn.boot.netty.constant.TerminalType;

/**
 * Netty服务端
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/15
 */
@Slf4j
public class NettyServer {

  /**
   * Netty配置属性
   */
  @Resource
  private final NettyServerProperties properties;

  /**
   * 创建bootstrap
   */
  private ServerBootstrap serverBootstrap;

  /**
   * BOSS
   */
  private EventLoopGroup boss;

  /**
   * Worker
   */
  private EventLoopGroup work;


  public NettyServer(NettyServerProperties properties) {
    this.properties = properties;
    this.serverBootstrap = new ServerBootstrap();
    this.boss = new NioEventLoopGroup();
    this.work = new NioEventLoopGroup();
  }

  /**
   * 关闭服务器方法
   */
  @PreDestroy
  public void close() {
    boss.shutdownGracefully();
    work.shutdownGracefully();
  }

  /**
   * 初始化
   */
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    ChannelInitializer<NioSocketChannel> channelInitializer = new ChannelInitializer<NioSocketChannel>() {

      @Override
      protected void initChannel(NioSocketChannel ch) {
        String[] tags = Tags.from(Terminal.class, TerminalType.SERVER)
            .add(Inbound.class, BoundType.AUTO).toArray();
        List<ChannelInboundHandler> inboundHandlers = BeanCaches
            .tagBeans(ChannelInboundHandler.class, tags);
        ch.pipeline()
            .addLast(new StringEncoder(), new StringDecoder())
            .addLast(inboundHandlers.toArray(new ChannelHandler[0]));
      }
    };

    serverBootstrap.group(boss, work)
        .channel(NioServerSocketChannel.class)
        .handler(new LoggingHandler(LogLevel.INFO))
        .childHandler(channelInitializer);
    if (properties.isAutoStart()) {
      start();
    }
  }

  /**
   * 启动
   */
  public void start() {
    serverBootstrap.bind(properties.getPort()).addListener(future -> {
      if (future.isSuccess()) {
        log.info("Netty|启动成功...");
      } else {
        log.info("Netty|启动失败...");
      }
    });
  }
}
