package site.morn.boot.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import site.morn.boot.netty.adapter.TerminalChannelAdapters.TerminalChannelInitializer;
import site.morn.boot.netty.config.NettyServerProperties;
import site.morn.boot.netty.constant.TerminalTypeConstants;

/**
 * Netty服务端
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
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
  @Delegate
  private final ServerBootstrap serverBootstrap;

  /**
   * BOSS
   */
  private final EventLoopGroup boss;

  /**
   * Worker
   */
  private final EventLoopGroup work;


  public NettyServer(NettyServerProperties properties) {
    this.properties = properties;
    this.serverBootstrap = new ServerBootstrap();
    this.boss = new NioEventLoopGroup();
    this.work = new NioEventLoopGroup();
  }

  /**
   * 销毁
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
    serverBootstrap
        .group(boss, work)
        .channel(NioServerSocketChannel.class)
        .handler(new LoggingHandler(LogLevel.INFO))
        .childHandler(new TerminalChannelInitializer(TerminalTypeConstants.SERVER));

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
        log.info("NettyServer|启动成功...");
      } else {
        log.info("NettyServer|启动失败...");
      }
    });
  }
}
