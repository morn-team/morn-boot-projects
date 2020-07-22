package site.morn.boot.netty.adapter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import site.morn.boot.netty.annotation.Inbound;
import site.morn.boot.netty.annotation.Terminal;
import site.morn.boot.netty.cache.ChannelIdentify;
import site.morn.boot.netty.cache.NettyCaches;
import site.morn.boot.netty.constant.BoundType;
import site.morn.boot.netty.constant.TerminalType;

/**
 * 缓存处理者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/5
 */
@Slf4j
@Sharable
@Inbound(BoundType.READER)
@Terminal(TerminalType.SERVER)
public class NettyCacheHandler extends SimpleChannelInboundHandler<ChannelIdentify> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ChannelIdentify identify) {
    Channel channel = ctx.channel();
    NettyCaches.putChannel(identify, channel);
  }

  @Override
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    super.channelRegistered(ctx);
    log.info("Netty|注册：{}", ctx.channel().id());
  }

  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    super.channelUnregistered(ctx);
    ChannelId id = ctx.channel().id();
    log.info("Netty|注销：{}", id);
    NettyCaches.removeAll(id);
  }
}
