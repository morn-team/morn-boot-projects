package site.morn.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 标准字典类
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/6
 */
public class CriteriaMap extends HashMap<String, Object> implements CriteriaAttributes {

  public CriteriaMap() {
    super();
  }

  public CriteriaMap(Map<String, Object> map) {
    super(map);
  }

  /**
   * 获取子集
   *
   * @param key 键
   * @return 值
   */
  public CriteriaMap getCriteriaMap(String key) {
    return getExpect(key);
  }
}
