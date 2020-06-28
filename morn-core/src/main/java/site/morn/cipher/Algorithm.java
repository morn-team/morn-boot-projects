package site.morn.cipher;

/**
 * 算法
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/29
 */
public interface Algorithm {

  /**
   * 算法名称
   */
  String getName();

  /**
   * 计算模式
   */
  String getMode();

  /**
   * 渲染风格
   */
  String getStyle();
}
