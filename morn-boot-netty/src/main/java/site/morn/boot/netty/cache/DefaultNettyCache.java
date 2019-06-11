package site.morn.boot.netty.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import site.morn.bean.annotation.Objective;
import site.morn.constant.ApplicationConstant.Cache;
import site.morn.util.TypeUtils;

/**
 * Netty缓存默认实现
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/4
 */
@Slf4j
@Objective
@CacheConfig(cacheNames = Cache.NETTY)
public class DefaultNettyCache implements NettyCache {

  @Cacheable(cacheNames = Cache.NETTY_CHANNEL, key = "#identify.toString()")
  @Override
  public Channel getChannel(ChannelIdentify identify) {
    ChannelGroup group = getGroup(identify.getBusinessGroup());
    ChannelId channelId = getChannelId(identify);
    return group.find(channelId);
  }

  @Cacheable(cacheNames = Cache.NETTY_GROUP)
  @Override
  public ChannelGroup getGroup(String name) {
    return new DefaultChannelGroup(name, GlobalEventExecutor.INSTANCE);
  }

  @Cacheable(cacheNames = Cache.NETTY_CHANNEL_IDENTIFY)
  @Override
  public ChannelIdentify getIdentify(ChannelId channelId) {
    return null;
  }

  @Override
  public <T extends NettyCache> T putChannel(ChannelIdentify identify, Channel channel) {
    Channel c = getChannel(identify); // 从缓存中获取消息通道
    if (Objects.nonNull(c) && c.isActive()) {
      return TypeUtils.as(this);
    }
    ChannelGroup group = getGroup(identify.getBusinessGroup());
    if (Objects.nonNull(c)) {
      group.remove(c);
    }
    setChannelId(identify, channel.id());
    group.add(channel);
    log.info("Netty|存入：{} at {}.{}", channel.id(), identify.getBusinessGroup(),
        identify.getBusinessKey());
    return TypeUtils.as(this);
  }

  @Override
  public <T extends NettyCache> T remove(ChannelId channelId) {
    if (Objects.isNull(channelId)) {
      return TypeUtils.as(this);
    }
    ChannelIdentify identify = getIdentify(channelId);
    removeChannel(identify, channelId); // 移除通道
    removeChannelId(identify); // 移除通道编号
    removeChannelIdentify(channelId); // 移除通道标识
    log.info("Netty|移除：{} at {}", channelId, getChannelPath(identify));
    return TypeUtils.as(this);
  }

  @Override
  public <T extends NettyCache> T remove(ChannelIdentify identify) {
    if (Objects.isNull(identify)) {
      return TypeUtils.as(this);
    }
    ChannelId channelId = getChannelId(identify);
    removeChannel(identify, channelId); // 移除通道
    removeChannelId(identify); // 移除通道编号
    removeChannelIdentify(channelId); // 移除通道标识
    log.info("Netty|移除：{} at {}", channelId, getChannelPath(identify));
    return TypeUtils.as(this);
  }

  /**
   * 获取通道编号
   *
   * <p>Cache
   *
   * @param identify 通道标识
   * @return 通道编号
   */
  @Cacheable(cacheNames = Cache.NETTY_CHANNEL_ID, key = "#identify.toString()")
  public ChannelId getChannelId(ChannelIdentify identify) {
    return null;
  }

  /**
   * 移除通道
   *
   * @param identify 通道标识
   * @param channelId 通道编号
   */
  @CacheEvict(cacheNames = Cache.NETTY_CHANNEL, key = "#identify.toString()")
  public void removeChannel(ChannelIdentify identify, ChannelId channelId) {
    if (Objects.isNull(identify) || Objects.isNull(channelId)) {
      return;
    }
    ChannelGroup group = getGroup(identify.getBusinessGroup());
    group.remove(channelId);
  }

  /**
   * 移除通道编号
   *
   * @param identify 通道标识
   */
  @SuppressWarnings("unsupported")
  @CacheEvict(cacheNames = Cache.NETTY_CHANNEL_ID, key = "#identify.toString()")
  public void removeChannelId(ChannelIdentify identify) {
  }

  /**
   * 移除通道标识
   *
   * @param channelId 通道编号
   */
  @CacheEvict(cacheNames = Cache.NETTY_CHANNEL_IDENTIFY, key = "#channelId.toString()")
  public void removeChannelIdentify(ChannelId channelId) {
  }


  /**
   * 缓存通道编号
   *
   * <p>Cache
   *
   * @param identify 通道标识
   * @param channelId 通道编号
   * @return 通道编号
   */
  @CachePut(cacheNames = Cache.NETTY_CHANNEL_ID, key = "#identify.toString()")
  public ChannelId setChannelId(ChannelIdentify identify, ChannelId channelId) {
    return channelId;
  }

  /**
   * 缓存通道标识
   *
   * <p>Cache
   *
   * @param channelId 通道编号
   * @param identify 通道标识
   * @return 通道标识
   */
  @CachePut(cacheNames = Cache.NETTY_CHANNEL_IDENTIFY, key = "#channelId.toString()")
  public ChannelIdentify setIdentify(ChannelId channelId, ChannelIdentify identify) {
    return identify;
  }

  /**
   * 获取通道路径
   *
   * @param identify 通道标识
   * @return 通道路径
   */
  private String getChannelPath(ChannelIdentify identify) {
    if (Objects.isNull(identify)) {
      return "none";
    }
    return identify.getBusinessGroup() + "." + identify.getBusinessKey();
  }
}
