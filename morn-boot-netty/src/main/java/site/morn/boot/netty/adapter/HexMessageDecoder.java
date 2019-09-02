package site.morn.boot.netty.adapter;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

/**
 * 十六进制消息解码器
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/13
 */
@Sharable
public class HexMessageDecoder extends MessageToMessageDecoder<byte[]> {

  @Override
  protected void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
    out.add(new HexMessage(msg));
  }
}
