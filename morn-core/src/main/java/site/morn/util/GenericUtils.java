package site.morn.util;

import java.lang.reflect.Method;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import site.morn.exception.ApplicationMessages;

/**
 * 泛型工具类
 *
 * @author timely-rain
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
@Slf4j
@UtilityClass
public class GenericUtils {

  /**
   * 类型转换
   *
   * @param source 原对象
   * @param <T> 目标类型
   * @return 目标对象
   */
  @SuppressWarnings("unchecked")
  public <T> T castFrom(Object source) {
    try {
      return (T) source;
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
      throw ApplicationMessages
          .translateMessage("util.cast-fail", source.getClass().getSimpleName()).exception();
    }
  }

  /**
   * 类型转换
   *
   * @param source 原对象
   * @param cls 目标类
   * @param <T> 目标类型
   * @return 目标对象
   */
  public <T> T castFrom(Object source, Class<T> cls) {
    try {
      return cls.cast(source);
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
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
        m = clazz.getDeclaredMethod("clone", (Class<?>[]) null);
      } catch (final NoSuchMethodException ex) {
        log.warn(ex.getMessage(), ex);
        throw ApplicationMessages.buildException("clone.no-method", ex.getMessage());
      }
      try {
        @SuppressWarnings("unchecked") // OK because clone() preserves the class
        final T result = (T) m.invoke(obj, (Object[]) null);
        return result;
      } catch (final Exception ex) {
        log.warn(ex.getMessage(), ex);
        final Throwable cause = ex.getCause();
        throw ApplicationMessages.buildException("clone.clone-failure", cause.getMessage());
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
  public static <T> T safeClone(final T obj) {
    try {
      return clone(obj);
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
      return obj;
    }
  }
}
