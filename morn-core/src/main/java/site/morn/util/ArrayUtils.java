package site.morn.util;

import java.util.Objects;

/**
 * 数组工具类
 *
 * @author timely-rain
 * @since 1.0.2, 2019/5/9
 */
public final class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

  /**
   * 合并为数组
   *
   * @param ts 不定项变量
   * @param <T> 变量类型
   * @return 数组
   */
  public static <T> T[] merge(T... ts) {
    return ts;
  }

  /**
   * 任意检索
   *
   * <p>sources为空时，返回true
   * <p>targets为空时，返回false
   * <p>当sources与targets有任意值匹配时，返回true
   *
   * @param sources 检索条件
   * @param targets 检索目标
   * @return 是否检索成功
   * @deprecated 语义不明确
   */
  @Deprecated
  public static boolean anySearch(String[] sources, String[] targets) {
    if (isEmpty(sources)) {
      return true;
    }
    if (isEmpty(targets)) {
      return false;
    }
    for (String source : sources) {
      for (String target : targets) {
        if (Objects.equals(source, target)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 获取第一个匹配项
   *
   * @param array 参数数组
   * @param cls 参数类型
   * @param <T> 参数泛型
   * @return 指定参数
   */
  @SuppressWarnings("unchecked")
  public static <T> T first(Object[] array, Class<T> cls) {
    for (Object argument : array) {
      if (Objects.isNull(argument)) {
        continue;
      }
      if (cls.isAssignableFrom(argument.getClass())) {
        return (T) argument;
      }
    }
    return null;
  }

  /**
   * 判断是否完全包含
   */
  public static boolean contains(Object[] sources, Object[] targets) {
    if (isEmpty(sources)) {
      return false;
    }
    if (isEmpty(targets)) {
      return true;
    }
    for (Object target : targets) {
      if (!contains(sources, target)) {
        return false;
      }
    }
    return true;
  }
}
