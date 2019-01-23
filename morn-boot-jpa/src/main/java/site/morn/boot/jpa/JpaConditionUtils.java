package site.morn.boot.jpa;

import lombok.experimental.UtilityClass;

/**
 * JPA动态查询工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/23
 */
@UtilityClass
public class JpaConditionUtils {

  /**
   * 包含筛选
   *
   * @param value 值
   * @return 条件字符串
   */
  public static String contains(Object value) {
    return like("%", value, "%");
  }

  /**
   * 开始筛选
   *
   * @param value 值
   * @return 条件字符串
   */
  public static String startWith(Object value) {
    return like("", value, "%");
  }

  /**
   * 结束筛选
   *
   * @param value 值
   * @return 条件字符串
   */
  public static String endWith(Object value) {
    return like("%", value, "");
  }

  /**
   * 模糊筛选
   *
   * @param value 值
   * @return 条件字符串
   */
  private static String like(String prefix, Object value, String suffix) {
    return prefix + value + suffix;
  }
}
