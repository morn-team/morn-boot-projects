package site.morn.boot.netty.adapter;

import site.morn.boot.netty.ChannelPoolIdentify;
import site.morn.boot.netty.NettyClient;

/**
 * Netty客户端生产者
 *
 * @param <I> 连接池标识类型
 * @author timely-rain
 * @since 1.2.2, 2020/9/14
 */
public interface NettyClientProducer<I extends ChannelPoolIdentify> {

  /**
   * 创建客户端
   *
   * @param identify 连接池标识
   * @return 客户端
   */
  NettyClient create(I identify);
}
