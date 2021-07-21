package site.morn.boot.netty.adapter;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import site.morn.boot.netty.annotation.NettyHandler;
import site.morn.boot.netty.annotation.NettyTerminal;
import site.morn.boot.netty.constant.HandlerTypeConstants;
import site.morn.boot.netty.constant.TerminalTypeConstants;

/**
 * 十六进制消息日志
 *
 * @author timely-rain
 * @since 1.2.2, 2020/9/15
 */
@Slf4j
@Sharable
@NettyHandler(HandlerTypeConstants.HANDLER)
@NettyTerminal(TerminalTypeConstants.BOTH)
public class HexMessageInboundHandler extends SimpleChannelInboundHandler<HexMessage> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HexMessage hexMessage) {
    log.info("Netty|In|Hex：{}", hexMessage.getMessage());
    ctx.fireChannelRead(hexMessage);
  }
}
