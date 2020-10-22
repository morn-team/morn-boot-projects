package site.morn.boot.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.nio.charset.Charset;
import java.util.List;
import org.springframework.util.Assert;
import site.morn.boot.netty.adapter.HexMessage;

/**
 * 十六进制消息解码器
 *
 * @author timely-rain
 * @since 1.2.2, 2020/9/14
 */
@Sharable
public class HexMessageStringDecoder extends MessageToMessageDecoder<ByteBuf> {

  private final Charset charset;

  /**
   * Creates a new instance with the current system character set.
   */
  public HexMessageStringDecoder() {
    this(Charset.defaultCharset());
  }

  /**
   * Creates a new instance with the specified character set.
   */
  public HexMessageStringDecoder(Charset charset) {
    Assert.notNull(charset, "Netty|Decoder|Charset不能为空。");
    this.charset = charset;
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
    String s = msg.toString(charset);
    out.add(new HexMessage(s));
  }
}
