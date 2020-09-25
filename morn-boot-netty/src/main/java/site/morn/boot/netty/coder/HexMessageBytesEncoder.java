package site.morn.boot.netty.coder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import site.morn.boot.netty.adapter.HexMessage;

/**
 * 十六进制消息编码器
 *
 * @author timely-rain
 * @since 1.2.2, 2020/9/15
 */
@Sharable
public class HexMessageBytesEncoder extends MessageToMessageEncoder<HexMessage> {


  @Override
  protected void encode(ChannelHandlerContext ctx, HexMessage msg, List<Object> out) {
    out.add(Unpooled.wrappedBuffer(msg.getArray()));
  }
}
