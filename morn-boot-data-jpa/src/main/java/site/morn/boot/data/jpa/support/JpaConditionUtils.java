package site.morn.boot.data.jpa.support;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import site.morn.boot.data.jpa.JpaPredicate;

/**
 * JPA动态查询工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/23
 */
@Slf4j
@UtilityClass
public class JpaConditionUtils {

  /**
   * 构建条件断言
   *
   * @param attribute 实体属性
   * @param value 属性值
   * @param function 条件断言构建方法
   * @param <T> 属性类型
   * @param <V> 值类型
   * @return 条件断言
   */
  public static <T, V> Predicate predicate(Expression<T> attribute, V value,
      BiFunction<Expression<T>, V, Predicate> function) {
    if (Objects.isNull(value)) {
      return null;
    }
    if (value instanceof String && StringUtils.isEmpty(value)) {
      return null;
    }
    return function.apply(attribute, value);
  }

  /**
   * 构建条件断言
   *
   * @param attributes 实体属性Stream
   * @param value 属性值
   * @param function 条件断言构建方法
   * @param <T> 属性类型
   * @param <V> 值类型
   * @return 条件断言数组
   */
  public static <T, V> Predicate[] predicate(Stream<Expression<T>> attributes, V value,
      BiFunction<Expression<T>, V, Predicate> function) {
    Stream<Predicate> predicateStream = attributes
        .map(expressions -> predicate(expressions, value, function));
    return JpaPredicate.array(predicateStream);
  }

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
    if (Objects.isNull(value)) {
      return null;
    }
    if (StringUtils.isEmpty(value)) {
      return "";
    }
    String p = Optional.ofNullable(prefix).orElse("");
    String s = Optional.ofNullable(suffix).orElse("");
    return String.format("%s%s%s", p, value, s);
  }

  /**
   * 获取对象的属性值
   *
   * @param model 对象
   * @param descriptor 属性描述
   * @param <T> 实体类类型
   * @return 属性值
   */
  public static <T> Object getPropertyValue(T model, PropertyDescriptor descriptor) {
    if (Objects.isNull(model) || Objects.isNull(descriptor)) {
      return null;
    }
    Method reader = descriptor.getReadMethod();
    try {
      return reader.invoke(model);
    } catch (IllegalAccessException | InvocationTargetException e) {
      log.warn(e.getMessage(), e);
      return null;
    }
  }


}
