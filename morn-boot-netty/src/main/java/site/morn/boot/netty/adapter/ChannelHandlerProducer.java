package site.morn.boot.netty.adapter;

import io.netty.channel.ChannelHandler;
import site.morn.core.BeanProducers;

/**
 * 通道处理器生产者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/14
 */
public interface ChannelHandlerProducer extends BeanProducers<ChannelHandler> {

}
