package site.morn.boot.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 通道写入器
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/18
 * @deprecated {@link NettyChannelOperations}
 */
@Slf4j
@Getter
@Setter
@Deprecated
@NoArgsConstructor
public class ChannelWriter {

  /**
   * 通道
   */
  protected Channel channel;

  public ChannelWriter(Channel channel) {
    this.channel = channel;
  }

  public ChannelFuture flush(Object msg) {
    log.info("Netty|Out：{}", msg);
    return channel.writeAndFlush(msg);
  }

  public ChannelFuture flush(Object msg, ChannelPromise promise) {
    log.info("Netty|Out：{}", msg);
    return channel.writeAndFlush(msg, promise);
  }

  public ChannelFuture write(Object msg) {
    return channel.write(msg);
  }

  public ChannelFuture write(Object msg, ChannelPromise promise) {
    return channel.write(msg, promise);
  }
}
