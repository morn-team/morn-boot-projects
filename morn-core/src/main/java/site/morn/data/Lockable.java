package site.morn.data;

import org.apache.commons.lang3.BooleanUtils;

/**
 * 可锁定的
 *
 * <p>默认为未锁定状态
 *
 * @author timely-rain
 * @since 2.1.0, 2019/8/13
 */
public interface Lockable {

  /**
   * 获取锁定状态
   */
  Boolean getLocked();

  /**
   * 设置锁定状态
   */
  void setLocked(Boolean isDisplay);

  /**
   * 判断是否为锁定状态
   */
  default boolean isLocked() {
    return BooleanUtils.isTrue(getLocked());
  }
}
