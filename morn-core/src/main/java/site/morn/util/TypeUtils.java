package site.morn.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.experimental.UtilityClass;
import site.morn.exception.ApplicationMessages;

/**
 * 类型工具类
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
@UtilityClass
public class TypeUtils {

  /**
   * 类型转换
   *
   * @param source 原对象
   * @param <T> 目标类型
   * @return 目标对象
   * @deprecated {@link #cast(Object)}
   */
  @Deprecated
  public <T> T as(Object source) {
    return cast(source);
  }

  /**
   * 类型转换
   *
   * @param source 原对象
   * @param <T> 目标类型
   * @return 目标对象
   */
  @SuppressWarnings("unchecked")
  public <T> T cast(Object source) {
    try {
      return (T) source;
    } catch (Exception e) {
      throw ApplicationMessages
          .translateMessage("util.cast-fail", source.getClass().getSimpleName()).exception();
    }
  }

  /**
   * 克隆
   *
   * @param obj 任意对象
   * @param <T> 对象类型
   * @return 克隆对象
   */
  public static <T> T clone(final T obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof Cloneable) {
      final Class<?> clazz = obj.getClass();
      final Method m;
      try {
        m = clazz.getMethod("clone", (Class<?>[]) null);
      } catch (final NoSuchMethodException ex) {
        throw ApplicationMessages.buildException("clone.no-method", ex.getMessage());
      }
      try {
        @SuppressWarnings("unchecked") // OK because clone() preserves the class
        final T result = (T) m.invoke(obj, (Object[]) null);
        return result;
      } catch (final InvocationTargetException ex) {
        final Throwable cause = ex.getCause();
        throw ApplicationMessages.buildException("clone.invoke-failure", cause.getMessage());
      } catch (final IllegalAccessException ex) {
        throw ApplicationMessages.buildException("clone.access-failure", ex.getMessage());
      }
    }
    throw ApplicationMessages.translateException("clone.not-cloneable");
  }

  /**
   * "软"克隆
   *
   * <p>当未实现 {@link Cloneable}、未重写 {@link Object#clone()} 时，不进行克隆
   *
   * @param obj 任意对象
   * @param <T> 对象类型
   * @return 克隆对象
   */
  public static <T> T cloneSoft(final T obj) {
    try {
      return clone(obj);
    } catch (Exception e) {
      return obj;
    }
  }
}
