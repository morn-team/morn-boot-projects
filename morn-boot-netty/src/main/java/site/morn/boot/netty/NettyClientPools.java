package site.morn.boot.netty;

import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.util.Assert;
import site.morn.bean.support.BeanCaches;
import site.morn.boot.netty.adapter.NettyClientProducer;

/**
 * 客户端连接池
 *
 * @author timely-rain
 * @since 1.2.2, 2020/9/14
 */
public class NettyClientPools {

  /**
   * 客户端字典
   */
  private static final ConcurrentMap<ChannelPoolIdentify, NettyClient> CLIENT_MAP = new ConcurrentHashMap<>();

  /**
   * 连接池字典
   */
  private static final ChannelPoolMap<ChannelPoolIdentify, FixedChannelPool> POOL_MAP = new AbstractChannelPoolMap<ChannelPoolIdentify, FixedChannelPool>() {
    @Override
    protected FixedChannelPool newPool(ChannelPoolIdentify key) {
      if (POOL_MAP.contains(key)) {
        return POOL_MAP.get(key);
      }
      return getClient(key).getChannelPool();
    }
  };

  private NettyClientPools() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * 获取连接池
   *
   * @param identify 连接池标识
   * @return 连接池
   */
  public static ChannelPool getChannelPool(ChannelPoolIdentify identify) {
    NettyClient client = getClient(identify);
    Assert.notNull(client, "无法获取Netty客户端，identify：" + identify);
    return client.getChannelPool();
  }

  /**
   * 获取客户端
   *
   * @param identify 连接池标识
   * @return 客户端
   */
  public static NettyClient getClient(ChannelPoolIdentify identify) {
    if (CLIENT_MAP.containsKey(identify)) {
      return CLIENT_MAP.get(identify);
    }
    return createClient(identify);
  }

  /**
   * 创建客户端
   *
   * @param identify 连接池标识
   * @return 客户端
   */
  public static NettyClient createClient(ChannelPoolIdentify identify) {
    NettyClientProducer<ChannelPoolIdentify> clientProducer = BeanCaches
        .targetBean(NettyClientProducer.class, identify.getClass());
    Assert.notNull(clientProducer, "尚未注册客户端生产者，identity：" + identify);
    NettyClient nettyClient = clientProducer.create(identify);
    nettyClient.init();
    CLIENT_MAP.put(identify, nettyClient);
    return nettyClient;
  }
}
