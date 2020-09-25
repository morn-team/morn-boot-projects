package site.morn.boot.netty.coder;

import io.netty.channel.ChannelHandler;
import site.morn.boot.netty.adapter.ChannelHandlerProducer;
import site.morn.boot.netty.annotation.NettyHandler;
import site.morn.boot.netty.annotation.NettyTerminal;
import site.morn.boot.netty.constant.HandlerType;
import site.morn.boot.netty.constant.TerminalType;

/**
 * Hex消息解码器生产者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/27
 */
@NettyHandler(HandlerType.DECODER)
@NettyTerminal(TerminalType.BOTH)
public class HexMessageStringDecoderProducer implements ChannelHandlerProducer {

  @Override
  public ChannelHandler[] products() {
    return new ChannelHandler[]{
        // ByteBuf(String)转HexMessage
        new HexMessageStringDecoder()
    };
  }
}
