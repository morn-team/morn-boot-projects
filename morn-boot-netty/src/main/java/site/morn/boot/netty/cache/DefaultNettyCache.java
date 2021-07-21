package site.morn.boot.netty.cache;

import static site.morn.constant.ApplicationConstants.Caches.CACHE_MANAGER_NAME_SIMPLE;

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
import site.morn.constant.ApplicationConstants.Caches;
import site.morn.util.GenericUtils;

/**
 * Netty缓存默认实现
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/4
 */
@Slf4j
@Objective
@CacheConfig(cacheNames = Caches.NETTY)
public class DefaultNettyCache implements NettyCache {

  @Override
  public Channel getChannel(ChannelIdentify identify) {
    ChannelGroup group = NettyCaches.getGroup(identify.getBusinessGroup());
    ChannelId channelId = NettyCaches.getChannelId(identify);
    if (Objects.isNull(channelId)) {
      return null;
    }
    return group.find(channelId);
  }

  @Override
  @Cacheable(cacheNames = Caches.NETTY_CHANNEL_ID, key = "#identify.toString()")
  public ChannelId getChannelId(ChannelIdentify identify) {
    log.info("NettyServer|NoChannelId at {}", getChannelPath(identify));
    return null;
  }

  @Override
  @Cacheable(cacheNames = Caches.NETTY_GROUP, cacheManager = CACHE_MANAGER_NAME_SIMPLE)
  public ChannelGroup getGroup(String name) {
    return new DefaultChannelGroup(name, GlobalEventExecutor.INSTANCE);
  }

  @Override
  @Cacheable(cacheNames = Caches.NETTY_CHANNEL_IDENTIFY, key = "#channelId.asShortText()")
  public ChannelIdentify getIdentify(ChannelId channelId) {
    log.info("NettyServer|NoChannelIdentify with {}", channelId.asShortText());
    return null;
  }

  @Override
  public <T extends NettyCache> T putChannel(ChannelIdentify identify, Channel channel) {
    Channel c = getChannel(identify); // 从缓存中获取消息通道
    if (Objects.nonNull(c) && c.isActive()) {
      return GenericUtils.castFrom(this);
    }
    ChannelGroup group = NettyCaches.getGroup(identify.getBusinessGroup());
    if (Objects.nonNull(c)) {
      group.remove(c);
    }
    NettyCaches.putChannelId(identify, channel.id());
    NettyCaches.putIdentify(channel.id(), identify);
    group.add(channel);
    return GenericUtils.castFrom(this);
  }

  @Override
  @CachePut(cacheNames = Caches.NETTY_CHANNEL_ID, key = "#identify.toString()")
  public ChannelId putChannelId(ChannelIdentify identify, ChannelId channelId) {
    log.info("NettyServer|UpdateChannelId：{} at {}.{}", channelId, identify.getBusinessGroup(),
        identify.getBusinessKey());
    return channelId;
  }

  @Override
  @CachePut(cacheNames = Caches.NETTY_CHANNEL_IDENTIFY, key = "#channelId.asShortText()")
  public ChannelIdentify putIdentify(ChannelId channelId, ChannelIdentify identify) {
    log.info("NettyServer|UpdateChannelIdentify：{} at {}.{}", channelId, identify.getBusinessGroup(),
        identify.getBusinessKey());
    return identify;
  }

  @Override
  public <T extends NettyCache> T removeAll(ChannelId channelId) {
    if (Objects.isNull(channelId)) {
      return GenericUtils.castFrom(this);
    }
    ChannelIdentify identify = NettyCaches.getIdentify(channelId);
    if (Objects.nonNull(identify)) {
      NettyCaches.removeChannel(identify, channelId); // 移除通道
      NettyCaches.removeChannelId(identify); // 移除通道编号
    }
    NettyCaches.removeIdentify(channelId); // 移除通道标识
    log.info("NettyServer|Remove all {} at {}", channelId, getChannelPath(identify));
    return GenericUtils.castFrom(this);
  }

  @Override
  public <T extends NettyCache> T removeAll(ChannelIdentify identify) {
    if (Objects.isNull(identify)) {
      return GenericUtils.castFrom(this);
    }
    ChannelId channelId = NettyCaches.getChannelId(identify);
    if (Objects.nonNull(channelId)) {
      NettyCaches.removeChannel(identify, channelId); // 移除通道
      NettyCaches.removeIdentify(channelId); // 移除通道标识
    }
    NettyCaches.removeChannelId(identify); // 移除通道编号
    log.info("NettyServer|Remove all {} at {}", channelId, getChannelPath(identify));
    return GenericUtils.castFrom(this);
  }

  /**
   * 移除通道
   *
   * @param identify  通道标识
   * @param channelId 通道编号
   */
  @Override
  public <T extends NettyCache> T removeChannel(ChannelIdentify identify, ChannelId channelId) {
    if (Objects.isNull(identify) || Objects.isNull(channelId)) {
      return GenericUtils.castFrom(this);
    }
    ChannelGroup group = NettyCaches.getGroup(identify.getBusinessGroup());
    Channel channel = group.find(channelId);
    group.remove(channel);
    log.info("NettyServer|RemoveChannel at {} {}", getChannelPath(identify), channelId.asShortText());
    return GenericUtils.castFrom(this);
  }

  /**
   * 移除通道编号
   *
   * @param identify 通道标识
   */
  @Override
  @CacheEvict(cacheNames = Caches.NETTY_CHANNEL_ID, key = "#identify.toString()")
  public <T extends NettyCache> T removeChannelId(ChannelIdentify identify) {
    log.info("NettyServer|RemoveChannelId at {}.{}", identify.getBusinessGroup(),
        identify.getBusinessKey());
    return GenericUtils.castFrom(this);
  }

  /**
   * 移除通道标识
   *
   * @param channelId 通道编号
   */
  @Override
  @CacheEvict(cacheNames = Caches.NETTY_CHANNEL_IDENTIFY, key = "#channelId.asShortText()")
  public <T extends NettyCache> T removeIdentify(ChannelId channelId) {
    log.info("NettyServer|RemoveChannelIdentify at {}", channelId.asShortText());
    return GenericUtils.castFrom(this);
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
