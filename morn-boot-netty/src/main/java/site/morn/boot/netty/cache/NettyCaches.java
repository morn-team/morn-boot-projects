package site.morn.boot.netty.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;
import site.morn.bean.support.BeanCaches;
import site.morn.util.GenericUtils;

/**
 * Netty缓存工具类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/5
 */
@UtilityClass
public class NettyCaches {

  public static Channel getChannel(ChannelIdentify identify) {
    return defaultCache().getChannel(identify);
  }

  public static ChannelId getChannelId(ChannelIdentify identify) {
    return defaultCache().getChannelId(identify);
  }

  public static ChannelGroup getGroup(String name) {
    return defaultCache().getGroup(name);
  }

  public static ChannelIdentify getIdentify(ChannelId channelId) {
    return defaultCache().getIdentify(channelId);
  }

  public static <T extends NettyCache> T putChannel(String businessGroup, String businessKey,
      Channel channel) {
    return defaultCache().putChannel(businessGroup, businessKey, channel);
  }

  public static <T extends NettyCache> T putChannel(ChannelIdentify identify, Channel channel) {
    return defaultCache().putChannel(identify, channel);
  }

  public static <T extends NettyCache> T putChannelId(ChannelIdentify identify,
      ChannelId channelId) {
    NettyCache nettyCache = defaultCache();
    nettyCache.putChannelId(identify, channelId);
    return GenericUtils.castFrom(nettyCache);
  }

  public static <T extends NettyCache> T remove(ChannelIdentify identify) {
    return defaultCache().remove(identify);
  }

  public static <T extends NettyCache> T remove(ChannelId channelId) {
    return defaultCache().remove(channelId);
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
