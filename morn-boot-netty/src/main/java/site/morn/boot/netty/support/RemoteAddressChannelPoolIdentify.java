package site.morn.boot.netty.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.morn.boot.netty.ChannelPoolIdentify;

/**
 * 远程连接标识
 *
 * @author timely-rain
 * @since 1.2.2, 2020/9/14
 */
@Data
@AllArgsConstructor
public class RemoteAddressChannelPoolIdentify implements ChannelPoolIdentify {

  /**
   * 远程地址
   */
  private String remoteHost;

  /**
   * 远程端口
   */
  private int remotePort;
}
