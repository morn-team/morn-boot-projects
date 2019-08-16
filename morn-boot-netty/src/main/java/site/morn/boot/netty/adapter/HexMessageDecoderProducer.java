package site.morn.boot.netty.adapter;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import site.morn.boot.netty.annotation.Inbound;
import site.morn.boot.netty.annotation.Terminal;
import site.morn.boot.netty.constant.BoundType;
import site.morn.boot.netty.constant.TerminalType;

/**
 * Hex消息解码器生产者
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/27
 */
@Inbound(BoundType.DECODER)
@Terminal(TerminalType.BOTH)
public class HexMessageDecoderProducer implements ChannelHandlerProducer {

  @Override
  public ChannelHandler[] products() {
    return new ChannelHandler[]{
        /*
        ByteBuf转byte[]
         */
        new ByteArrayDecoder(),
        /*
        byte[]转HexMessage
         */
        new HexMessageDecoder()
    };
  }
}
