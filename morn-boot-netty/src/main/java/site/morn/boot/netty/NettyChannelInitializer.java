package site.morn.boot.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import java.util.List;
import site.morn.bean.BeanCaches;
import site.morn.bean.Tags;
import site.morn.boot.netty.adapter.ChannelHandlerProducer;
import site.morn.boot.netty.annotation.Inbound;
import site.morn.boot.netty.annotation.Terminal;
import site.morn.boot.netty.constant.BoundType;
import site.morn.util.BeanFunctionUtils;

/**
 * 通道初始化
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/12
 */
public class NettyChannelInitializer extends ChannelInitializer {

  /**
   * 终端类型
   *
   * @see site.morn.boot.netty.constant.TerminalType
   */
  private final String terminal;

  public NettyChannelInitializer(String terminal) {
    this.terminal = terminal;
  }

  @Override
  protected void initChannel(Channel ch) {
    ChannelPipeline pipeline = ch.pipeline();

    registerChannelHandler(pipeline, BoundType.DECODER); // 注册解码器
    registerChannelHandler(pipeline, BoundType.ENCODER); // 注册编码器
    registerChannelHandler(pipeline, BoundType.READER); // 注册读取器
  }

  /**
   * 注册通道处理者
   *
   * @param pipeline 流水线
   * @param boundType 处理类型
   */
  private void registerChannelHandler(ChannelPipeline pipeline, String boundType) {
    // 添加解码器
    String[] tags = Tags.from(Terminal.class, terminal).add(Inbound.class, boundType)
        .toArray();
    List<ChannelHandler> handlers = BeanCaches.tagBeans(ChannelHandler.class, tags);
    pipeline.addLast(handlers.toArray(new ChannelHandler[0]));

    // 添加解码器，通过实例生产者获取
    String[] productTags = Tags.from(Terminal.class, terminal).add(Inbound.class, boundType)
        .toArray();
    ChannelHandler[] productHandlers = BeanFunctionUtils
        .products(ChannelHandlerProducer.class, productTags).toArray(new ChannelHandler[0]);
    pipeline.addLast(productHandlers);
  }
}
