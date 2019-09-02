package site.morn.data;

/**
 * 可显示的
 *
 * <p>默认为显示状态
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/13
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
}
