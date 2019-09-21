package site.morn.boot.netty.cache;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * 通道标识
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/5
 */
@Builder
@Data
public class ChannelIdentify implements Serializable {

  /**
   * @serialField
   */
  private static final long serialVersionUID = 1L;

  /**
   * 业务分组
   */
  private String businessGroup;

  /**
   * 业务编号
   */
  private String businessKey;
}
