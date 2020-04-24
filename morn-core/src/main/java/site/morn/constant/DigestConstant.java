package site.morn.constant;

import lombok.experimental.UtilityClass;

/**
 * 数据加密常量
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
@UtilityClass
public class DigestConstant {

  /**
   * 数据加密算法
   */
  @UtilityClass
  public class Algorithms {

    /**
     * MD5算法
     */
    public final String MD5 = "md5";

    /**
     * BCrypt算法
     */
    public final String SPRING_B_CRYPT = "spring.b.crypt";
  }
}
