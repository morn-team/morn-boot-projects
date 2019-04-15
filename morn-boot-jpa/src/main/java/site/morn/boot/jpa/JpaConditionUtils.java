package site.morn.boot.jpa;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.criteria.Predicate;
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

  /**
   * 获取对象的属性值
   *
   * @param model 对象
   * @param descriptor 属性描述
   * @param <T> 实体类类型
   * @return 属性值
   */
  public static <T> Object getPropertyValue(T model,
      PropertyDescriptor descriptor) {
    if (Objects.isNull(model)) {
      return null;
    }
    Method reader = descriptor.getReadMethod();
    try {
      return reader.invoke(model);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * 将断言流转为数组
   *
   * @param stream 断言流
   * @return 断言数组
   */
  public static Predicate[] array(Stream<Predicate> stream) {
    return stream.filter(Objects::nonNull).toArray(Predicate[]::new);
  }

}
