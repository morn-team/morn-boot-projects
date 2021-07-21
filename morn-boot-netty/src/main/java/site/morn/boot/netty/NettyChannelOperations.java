package site.morn.boot.netty;

import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPool;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import org.springframework.util.Assert;
import site.morn.boot.netty.support.FutureAccessor;

/**
 * Netty通信工具类
 *
 * @author timely-rain
 * @since 1.2.2, 2020/9/15
 */
public class NettyChannelOperations {

  private NettyChannelOperations() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * 获取通道
   *
   * @param identify 连接池标识
   * @return 异步连接通道
   */
  public static FutureAccessor<Channel> acquireChannel(ChannelPoolIdentify identify) {
    ChannelPool channelPool = NettyClientPools.getChannelPool(identify);
    Assert.notNull(channelPool, "无法获取Netty连接池，identify：" + identify);
    return FutureAccessor.from(channelPool.acquire());
  }

  /**
   * 释放通道
   *
   * @param identify 连接池标识
   * @return 异步操作对象
   */
  public static FutureAccessor<Void> releaseChannel(ChannelPoolIdentify identify, Channel channel) {
    ChannelPool channelPool = NettyClientPools.getChannelPool(identify);
    Assert.notNull(channelPool, "无法获取Netty连接池，identify：" + identify);
    return FutureAccessor.from(channelPool.release(channel));
  }

  /**
   * 提交消息
   *
   * @param identify 连接池标识
   * @param channel  连接通道
   * @return 异步操作对象
   */
  public static FutureAccessor<Void> flush(ChannelPoolIdentify identify, Channel channel) {
    channel.flush();
    Future<Void> future = NettyClientPools.getChannelPool(identify).release(channel);
    return FutureAccessor.from(future);
  }

  /**
   * 发送消息
   * <p>调用{@link #flush(ChannelPoolIdentify, Channel)}提交消息</p>
   *
   * @param identify 连接池标识
   * @return 异步连接通道
   */
  public static FutureAccessor<Channel> write(ChannelPoolIdentify identify, Object msg) {
    return acquireChannel(identify).completeThen(channel -> channel.write(msg));
  }

  /**
   * 发送并提交消息
   * <p>{@link Channel#flush()}和{@link ChannelPool#release(Channel)}将会优先执行，因此{@link
   * FutureAccessor<Channel>}操作的{@link Channel}总是处于释放状态</p>
   *
   * @param identify 连接池标识
   */
  public static FutureAccessor<Channel> writeAndFlush(ChannelPoolIdentify identify, Object msg) {
    return useChannel(identify, channel -> channel.writeAndFlush(msg));
  }

  /**
   * 获取通道，完成操作后释放
   * <p>{@link ChannelPool#release(Channel)}将会优先执行，因此{@link FutureAccessor<Channel>}操作的{@link
   * Channel}总是处于释放状态</p>
   *
   * @param identify 连接池标识
   * @param consumer 通道消费函数
   */
  private static FutureAccessor<Channel> useChannel(ChannelPoolIdentify identify,
      Consumer<Channel> consumer) {
    ChannelPool channelPool = NettyClientPools.getChannelPool(identify);
    return FutureAccessor.from(channelPool.acquire()).completeThen(channel -> {
      consumer.accept(channel);
      channelPool.release(channel);
    });
  }
}
