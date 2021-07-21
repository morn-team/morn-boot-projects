package site.morn.boot.netty.adapter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.AbstractChannelPoolHandler;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import site.morn.bean.support.BeanCaches;
import site.morn.bean.support.Tags;
import site.morn.boot.netty.annotation.NettyHandler;
import site.morn.boot.netty.annotation.NettyTerminal;
import site.morn.boot.netty.constant.HandlerTypeConstants;
import site.morn.boot.netty.constant.TerminalTypeConstants;
import site.morn.util.BeanFunctionUtils;


/**
 * 终端连接适配器
 *
 * @author timely-rain
 * @since 1.2.2, 2020/9/14
 */
public class TerminalChannelAdapters {

  private TerminalChannelAdapters() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * 注册通道处理者
   *
   * @param terminal  终端类型
   * @param pipeline  流水线
   * @param boundType 处理类型
   */
  private static void registerChannelHandler(String terminal, ChannelPipeline pipeline,
      String boundType) {
    // 添加解码器
    String[] tags = Tags.from(NettyTerminal.class, terminal).add(NettyHandler.class, boundType)
        .toArray();
    List<ChannelHandler> handlers = BeanCaches.tagBeans(ChannelHandler.class, tags);
    pipeline.addLast(handlers.toArray(new ChannelHandler[0]));

    // 添加解码器，通过实例生产者获取
    String[] productTags = Tags.from(NettyTerminal.class, terminal)
        .add(NettyHandler.class, boundType)
        .toArray();
    ChannelHandler[] productHandlers = BeanFunctionUtils
        .batchProducts(ChannelHandlerProducer.class, productTags).toArray(new ChannelHandler[0]);
    pipeline.addLast(productHandlers);
  }

  /**
   * 终端通道初始化
   *
   * @author timely-rain
   * @since 1.2.0, 2019/6/12
   */
  public static class TerminalChannelInitializer extends ChannelInitializer<Channel> {

    /**
     * 终端类型
     *
     * @see TerminalTypeConstants
     */
    private final String terminal;

    public TerminalChannelInitializer(String terminal) {
      this.terminal = terminal;
    }

    @Override
    protected void initChannel(Channel ch) {
      ChannelPipeline pipeline = ch.pipeline();

      registerChannelHandler(terminal, pipeline, HandlerTypeConstants.SPLITTER); // 注册截断器
      registerChannelHandler(terminal, pipeline, HandlerTypeConstants.DECODER); // 注册解码器
      registerChannelHandler(terminal, pipeline, HandlerTypeConstants.ENCODER); // 注册编码器
      registerChannelHandler(terminal, pipeline, HandlerTypeConstants.HANDLER); // 注册处理者
    }

  }

  /**
   * 终端连接池处理者
   *
   * @author timely-rain
   * @since 1.2.2, 2020/9/14
   */
  @Slf4j
  public static class TerminalChannelPoolHandler extends AbstractChannelPoolHandler {

    /**
     * 终端类型
     *
     * @see TerminalTypeConstants
     */
    private final String terminal;

    public TerminalChannelPoolHandler(String terminal) {
      this.terminal = terminal;
    }

    @Override
    public void channelCreated(Channel ch) {
      ChannelPipeline pipeline = ch.pipeline();

      registerChannelHandler(terminal, pipeline, HandlerTypeConstants.SPLITTER); // 注册截断器
      registerChannelHandler(terminal, pipeline, HandlerTypeConstants.DECODER); // 注册解码器
      registerChannelHandler(terminal, pipeline, HandlerTypeConstants.ENCODER); // 注册编码器
      registerChannelHandler(terminal, pipeline, HandlerTypeConstants.HANDLER); // 注册处理者
    }

    @Override
    public void channelAcquired(Channel ch) throws Exception {
      super.channelAcquired(ch);
      log.info("NettyClient|Acquired：{}", ch.id());
    }

    @Override
    public void channelReleased(Channel ch) throws Exception {
      super.channelReleased(ch);
      log.info("NettyClient|Released：{}", ch.id());
    }
  }
}
