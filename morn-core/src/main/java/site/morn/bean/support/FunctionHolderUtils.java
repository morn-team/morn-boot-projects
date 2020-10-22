package site.morn.bean.support;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import site.morn.bean.FunctionHolder;

/**
 * 实例函数调用类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/19
 */
@Slf4j
@UtilityClass
public class FunctionHolderUtils {

  /**
   * 调用方法
   *
   * @param holders 函数持有者
   * @param args    实参
   * @param <T>     返回类型
   * @return 返回值
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> call(List<FunctionHolder> holders, Object... args) {
    return holders.stream().map(holder -> call(holder, args))
        .map(o -> Objects.isNull(o) ? null : (T) o)
        .collect(Collectors.toList());
  }

  /**
   * 调用方法
   *
   * @param holder 函数持有者
   * @param args 实参
   * @param <T> 返回类型
   * @return 返回值
   */
  @SuppressWarnings("unchecked")
  public static <T> T call(FunctionHolder holder, Object... args) {
    try {
      Object[] objects = availableArgs(holder.getParameterTypes(), args);
      Object result = holder.getMethod().invoke(holder.getBean(), objects);
      if (Objects.isNull(result)) {
        return null;
      }
      return (T) result;
    } catch (IllegalAccessException e) {
      log.warn("函数无法访问：{}", holder.getMethodPath());
      log.warn(e.getCause().getMessage(), e.getCause());
    } catch (InvocationTargetException e) {
      log.warn("函数调用失败：{}", holder.getMethodPath());
      log.warn(e.getCause().getMessage(), e.getCause());
    }
    return null;
  }

  /**
   * 检索可用实参
   *
   * @param parameterTypes 方法形参
   * @param args 方法实参
   * @return 可用实参
   */
  private Object[] availableArgs(Class<?>[] parameterTypes, Object[] args) {
    if (ObjectUtils.isEmpty(parameterTypes)) {
      return new Object[0];
    }
    // 检索可用实参
    Function<Class<?>, Object> availableArg = type -> Stream.of(args).filter(Objects::nonNull)
        .filter(o -> type.isAssignableFrom(o.getClass())).findFirst().orElse(null);
    return Stream.of(parameterTypes).map(availableArg).toArray();
  }
}
