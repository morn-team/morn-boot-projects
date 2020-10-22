package site.morn.boot.netty.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import site.morn.boot.netty.ChannelPoolIdentify;

/**
 * Netty缓存
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/4
 */
public interface NettyCache {

  /**
   * 获取消息通道
   *
   * @param identify 通道标识
   * @return 消息通道
   */
  Channel getChannel(ChannelIdentify identify);

  /**
   * 获取通道编号
   *
   * @param identify 通道标识
   * @return 通道编号
   */
  ChannelId getChannelId(ChannelIdentify identify);

  /**
   * 获取通道分组
   *
   * @param name 分组名称
   * @return 通道分组
   */
  ChannelGroup getGroup(String name);

  /**
   * 获取通道分组
   *
   * @param poolIdentify 连接池标识
   * @return 通道分组
   */
  default ChannelGroup getGroup(ChannelPoolIdentify poolIdentify) {
    return getGroup(String.valueOf(poolIdentify.hashCode()));
  }

  /**
   * 获取通道标识
   *
   * @param channelId 通道编号
   * @return 通道标识
   */
  ChannelIdentify getIdentify(ChannelId channelId);

  /**
   * 缓存消息通道
   *
   * @param identify 通道标识
   * @param channel  消息通道
   */
  <T extends NettyCache> T putChannel(ChannelIdentify identify, Channel channel);

  /**
   * 缓存消息通道
   *
   * @param businessGroup 业务分组
   * @param businessKey   业务编号
   * @param channel       消息通道
   */
  default <T extends NettyCache> T putChannel(String businessGroup, String businessKey,
      Channel channel) {
    ChannelIdentify identify = ChannelIdentify.builder().businessGroup(businessGroup)
        .businessKey(businessKey).build();
    return putChannel(identify, channel);
  }

  /**
   * 缓存通道编号
   *
   * @param identify  通道标识
   * @param channelId 通道编号
   * @return 通道编号
   */
  ChannelId putChannelId(ChannelIdentify identify, ChannelId channelId);

  /**
   * 缓存通道标识
   *
   * @param channelId 通道编号
   * @param identify  通道标识
   * @return 通道标识
   */
  ChannelIdentify putIdentify(ChannelId channelId, ChannelIdentify identify);

  /**
   * 移除通道编号、通道标识的缓存信息，并从{@link ChannelGroup}中移除{@link Channel}
   *
   * @param channelId 通道编号
   */
  <T extends NettyCache> T removeAll(ChannelId channelId);

  /**
   * 移除通道编号、通道标识的缓存信息，并从{@link ChannelGroup}中移除{@link Channel}
   *
   * @param identify 通道标识
   */
  <T extends NettyCache> T removeAll(ChannelIdentify identify);

  /**
   * 移除通道，即从{@link ChannelGroup}中移除{@link Channel}
   *
   * @param identify  通道标识
   * @param channelId 通道编号
   */
  <T extends NettyCache> T removeChannel(ChannelIdentify identify, ChannelId channelId);

  /**
   * 移除通道编号的缓存信息
   *
   * @param identify 通道标识
   */
  <T extends NettyCache> T removeChannelId(ChannelIdentify identify);

  /**
   * 移除通道标识的缓存信息
   *
   * @param channelId 通道编号
   */
  <T extends NettyCache> T removeIdentify(ChannelId channelId);
}
