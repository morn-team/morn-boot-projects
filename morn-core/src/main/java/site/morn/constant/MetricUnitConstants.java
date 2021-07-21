package site.morn.constant;

import lombok.experimental.UtilityClass;

/**
 * 度量单位
 *
 * @author timely-rain
 * @since 1.0.0, 2018/10/25
 */
@UtilityClass
public class MetricUnitConstants {

  /**
   * 文本单位
   */
  public static class Texts {

    /**
     * 短文本
     */
    public static final int SHORT = 32;

    /**
     * 一般文本
     */
    public static final int NORMAL = 255;

    /**
     * 长文本
     */
    public static final int LONG = 1024;

    private Texts() {
    }
  }

}
