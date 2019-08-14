package site.morn.data;

import org.apache.commons.lang3.BooleanUtils;

/**
 * 可显示的
 *
 * <p>默认为显示状态
 *
 * @author timely-rain
 * @since 2.1.0, 2019/8/13
 */
public interface Displayable {

  /**
   * 获取显示状态
   */
  Boolean getDisplay();

  /**
   * 设置显示状态
   */
  void setDisplay(Boolean isDisplay);

  /**
   * 判断是否为显示状态
   */
  default boolean isDisplay() {
    return !BooleanUtils.isFalse(getDisplay());
  }
}
