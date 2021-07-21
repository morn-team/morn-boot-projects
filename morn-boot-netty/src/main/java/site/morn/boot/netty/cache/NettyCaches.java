package site.morn.boot.netty.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import org.springframework.util.Assert;
import site.morn.bean.support.BeanCaches;
import site.morn.util.GenericUtils;

/**
 * Netty缓存工具类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/5
 */
public class NettyCaches {

  private NettyCaches() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * 获取消息通道
   *
   * @param identify 通道标识
   * @return 消息通道
   */
  public static Channel getChannel(ChannelIdentify identify) {
    return defaultCache().getChannel(identify);
  }

  /**
   * 获取通道编号
   *
   * @param identify 通道标识
   * @return 通道编号
   */
  public static ChannelId getChannelId(ChannelIdentify identify) {
    return defaultCache().getChannelId(identify);
  }

  /**
   * 获取通道分组
   *
   * @param name 分组名称
   * @return 通道分组
   */
  public static ChannelGroup getGroup(String name) {
    return defaultCache().getGroup(name);
  }

  /**
   * 获取通道标识
   *
   * @param channelId 通道编号
   * @return 通道标识
   */
  public static ChannelIdentify getIdentify(ChannelId channelId) {
    return defaultCache().getIdentify(channelId);
  }

  /**
   * 缓存消息通道
   *
   * @param businessGroup 业务分组
   * @param businessKey   业务编号
   * @param channel       消息通道
   */
  public static <T extends NettyCache> T putChannel(String businessGroup, String businessKey,
      Channel channel) {
    return defaultCache().putChannel(businessGroup, businessKey, channel);
  }

  /**
   * 缓存消息通道
   *
   * @param identify 通道标识
   * @param channel  消息通道
   */
  public static <T extends NettyCache> T putChannel(ChannelIdentify identify, Channel channel) {
    return defaultCache().putChannel(identify, channel);
  }

  /**
   * 缓存通道编号
   *
   * @param identify  通道标识
   * @param channelId 通道编号
   * @return 通道编号
   */
  public static <T extends NettyCache> T putChannelId(ChannelIdentify identify,
      ChannelId channelId) {
    NettyCache nettyCache = defaultCache();
    nettyCache.putChannelId(identify, channelId);
    return GenericUtils.castFrom(nettyCache);
  }

  /**
   * 缓存通道标识
   *
   * @param channelId 通道编号
   * @param identify  通道标识
   * @return 通道标识
   */
  public static <T extends NettyCache> T putIdentify(ChannelId channelId,
      ChannelIdentify identify) {
    NettyCache nettyCache = defaultCache();
    nettyCache.putIdentify(channelId, identify);
    return GenericUtils.castFrom(nettyCache);
  }

  /**
   * 移除通道编号、通道标识的缓存信息，并从{@link ChannelGroup}中移除{@link Channel}
   *
   * @param channelId 通道编号
   */
  public static <T extends NettyCache> T removeAll(ChannelId channelId) {
    return defaultCache().removeAll(channelId);
  }

  /**
   * 移除通道编号、通道标识的缓存信息，并从{@link ChannelGroup}中移除{@link Channel}
   *
   * @param identify 通道标识
   */
  public static <T extends NettyCache> T removeAll(ChannelIdentify identify) {
    return defaultCache().removeAll(identify);
  }

  /**
   * 移除通道，即从{@link ChannelGroup}中移除{@link Channel}
   *
   * @param identify  通道标识
   * @param channelId 通道编号
   */
  public static <T extends NettyCache> T removeChannel(ChannelIdentify identify,
      ChannelId channelId) {
    return defaultCache().removeChannel(identify, channelId);
  }

  /**
   * 移除通道编号的缓存信息
   *
   * @param identify 通道标识
   */
  public static <T extends NettyCache> T removeChannelId(ChannelIdentify identify) {
    return defaultCache().removeChannelId(identify);
  }

  /**
   * 移除通道标识的缓存信息
   *
   * @param channelId 通道编号
   */
  public static <T extends NettyCache> T removeIdentify(ChannelId channelId) {
    return defaultCache().removeIdentify(channelId);
  }

  /**
   * 获取默认缓存
   *
   * @return 默认缓存
   */
  private static NettyCache defaultCache() {
    NettyCache nettyCache = BeanCaches.tagBean(NettyCache.class);
    Assert.notNull(nettyCache, "无法获取Netty缓存");
    return nettyCache;
  }
}
