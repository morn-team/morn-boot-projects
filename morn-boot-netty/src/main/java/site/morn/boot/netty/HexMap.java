package site.morn.boot.netty;

import site.morn.boot.netty.adapter.HexMessage;
import site.morn.core.CriteriaMap;

/**
 * 十六进制字典
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/19
 */
public class HexMap extends CriteriaMap {

  /**
   * 获取十六进制消息
   *
   * @param key 键
   * @return 十六进制消息
   */
  public HexMessage getHexMessage(String key) {
    return getExpect(key);
  }
}
