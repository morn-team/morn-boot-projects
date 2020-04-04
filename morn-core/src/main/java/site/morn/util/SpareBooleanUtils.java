package site.morn.util;

import lombok.experimental.UtilityClass;

/**
 * 备用布尔工具类
 *
 * <p>备用类不推荐在生产项目中使用，生产项目中推荐使用commons-lang3相关工具包
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/5
 */
@UtilityClass
public class SpareBooleanUtils {

  // boolean Boolean methods
  //-----------------------------------------------------------------------

  /**
   * <p>Checks if a {@code Boolean} value is {@code true},
   * handling {@code null} by returning {@code false}.</p>
   *
   * <pre>
   *   BooleanUtils.isTrue(Boolean.TRUE)  = true
   *   BooleanUtils.isTrue(Boolean.FALSE) = false
   *   BooleanUtils.isTrue(null)          = false
   * </pre>
   *
   * @param bool the boolean to check, null returns {@code false}
   * @return {@code true} only if the input is non-null and true
   * @since 2.1
   */
  public static boolean isTrue(final Boolean bool) {
    return Boolean.TRUE.equals(bool);
  }

  /**
   * <p>Checks if a {@code Boolean} value is <i>not</i> {@code true},
   * handling {@code null} by returning {@code true}.</p>
   *
   * <pre>
   *   BooleanUtils.isNotTrue(Boolean.TRUE)  = false
   *   BooleanUtils.isNotTrue(Boolean.FALSE) = true
   *   BooleanUtils.isNotTrue(null)          = true
   * </pre>
   *
   * @param bool the boolean to check, null returns {@code true}
   * @return {@code true} if the input is null or false
   * @since 2.3
   */
  public static boolean isNotTrue(final Boolean bool) {
    return !isTrue(bool);
  }

  /**
   * <p>Checks if a {@code Boolean} value is {@code false},
   * handling {@code null} by returning {@code false}.</p>
   *
   * <pre>
   *   BooleanUtils.isFalse(Boolean.TRUE)  = false
   *   BooleanUtils.isFalse(Boolean.FALSE) = true
   *   BooleanUtils.isFalse(null)          = false
   * </pre>
   *
   * @param bool the boolean to check, null returns {@code false}
   * @return {@code true} only if the input is non-null and false
   * @since 2.1
   */
  public static boolean isFalse(final Boolean bool) {
    return Boolean.FALSE.equals(bool);
  }

  /**
   * <p>Checks if a {@code Boolean} value is <i>not</i> {@code false},
   * handling {@code null} by returning {@code true}.</p>
   *
   * <pre>
   *   BooleanUtils.isNotFalse(Boolean.TRUE)  = true
   *   BooleanUtils.isNotFalse(Boolean.FALSE) = false
   *   BooleanUtils.isNotFalse(null)          = true
   * </pre>
   *
   * @param bool the boolean to check, null returns {@code true}
   * @return {@code true} if the input is null or true
   * @since 2.3
   */
  public static boolean isNotFalse(final Boolean bool) {
    return !isFalse(bool);
  }
}
