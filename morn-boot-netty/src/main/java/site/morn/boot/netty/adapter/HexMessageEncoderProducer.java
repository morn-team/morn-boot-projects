package site.morn.boot.netty.adapter;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringEncoder;
import site.morn.boot.netty.annotation.Inbound;
import site.morn.boot.netty.annotation.Terminal;
import site.morn.boot.netty.constant.BoundType;
import site.morn.boot.netty.constant.TerminalType;

/**
 * Hex消息编码器生产者
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/27
 */
@Inbound(BoundType.ENCODER)
@Terminal(TerminalType.BOTH)
public class HexMessageEncoderProducer implements ChannelHandlerProducer {

  @Override
  public ChannelHandler[] products() {
    return new ChannelHandler[]{
        /* String转ByteBuf */
        new StringEncoder(),
        /* HexMessage转ByteBuf */
        new HexMessageEncoder()
    };
  }
}
