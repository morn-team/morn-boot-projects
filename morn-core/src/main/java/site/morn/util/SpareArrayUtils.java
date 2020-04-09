package site.morn.util;

import java.lang.reflect.Array;
import java.util.Objects;
import lombok.experimental.UtilityClass;

/**
 * 备用数组工具类
 *
 * <p>备用类不推荐在生产项目中使用，生产项目中推荐使用commons-lang3相关工具包
 *
 * @author timely-rain
 * @since 1.0.2, 2019/5/9
 */
@UtilityClass
public final class SpareArrayUtils {

  /**
   * The index value when an element is not found in a list or array: {@code -1}. This value is
   * returned by methods in this class and can also be used in comparisons with values returned by
   * various method from {@link java.util.List}.
   */
  public static final int INDEX_NOT_FOUND = -1;

  // Generic array
  //-----------------------------------------------------------------------

  /**
   * <p>Create a type-safe generic array.
   *
   * <p>The Java language does not allow an array to be created from a generic type:
   *
   * <pre>
   * public static &lt;T&gt; T[] createAnArray(int size) {
   * return new T[size]; // compiler error here
   * }
   * public static &lt;T&gt; T[] createAnArray(int size) {
   * return (T[]) new Object[size]; // ClassCastException at runtime
   * }
   * </pre>
   *
   * <p>Therefore new arrays of generic types can be created with this method.
   * For example, an array of Strings can be created:
   *
   * <pre>
   * String[] array = ArrayUtils.toArray("1", "2");
   * String[] emptyArray = ArrayUtils.&lt;String&gt;toArray();
   * </pre>
   *
   * <p>The method is typically used in scenarios, where the caller itself uses generic types
   * that have to be combined into an array.
   *
   * <p>Note, this method makes only sense to provide arguments of the same type so that the
   * compiler can deduce the type of the array itself. While it is possible to select the type
   * explicitly like in
   * <code>Number[] array = ArrayUtils.&lt;Number&gt;toArray(Integer.valueOf(42),
   * Double.valueOf(Math.PI))</code>, there is no real advantage when compared to
   * <code>new Number[] {Integer.valueOf(42), Double.valueOf(Math.PI)}</code>.
   *
   * @param <T> the array's element type
   * @param items the varargs array items, null allowed
   * @return the array, not null unless a null array is passed in
   * @since 3.0
   */
  public static <T> T[] toArray(final T... items) {
    return items;
  }

  /**
   * <p>Returns the length of the specified array.
   * This method can deal with {@code Object} arrays and with primitive arrays.
   *
   * <p>If the input array is {@code null}, {@code 0} is returned.
   *
   * <pre>
   * ArrayUtils.getLength(null)            = 0
   * ArrayUtils.getLength([])              = 0
   * ArrayUtils.getLength([null])          = 1
   * ArrayUtils.getLength([true, false])   = 2
   * ArrayUtils.getLength([1, 2, 3])       = 3
   * ArrayUtils.getLength(["a", "b", "c"]) = 3
   * </pre>
   *
   * @param array the array to retrieve the length from, may be null
   * @return The length of the array, or {@code 0} if the array is {@code null}
   * @throws IllegalArgumentException if the object argument is not an array.
   * @since 2.1
   */
  public static int getLength(final Object array) {
    if (array == null) {
      return 0;
    }
    return Array.getLength(array);
  }

  // IndexOf search
  // ----------------------------------------------------------------------

  // Object IndexOf
  //-----------------------------------------------------------------------

  /**
   * <p>Finds the index of the given object in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
   *
   * @param array the array to search through for the object, may be {@code null}
   * @param objectToFind the object to find, may be {@code null}
   * @return the index of the object within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not
   * found or {@code null} array input
   */
  public static int indexOf(final Object[] array, final Object objectToFind) {
    return indexOf(array, objectToFind, 0);
  }

  /**
   * <p>Finds the index of the given object in the array starting at the given index.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the array
   * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array the array to search through for the object, may be {@code null}
   * @param objectToFind the object to find, may be {@code null}
   * @param startIndex the index to start searching at
   * @return the index of the object within the array starting at the index, {@link
   * #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(final Object[] array, final Object objectToFind, int startIndex) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    for (int i = Math.max(0, startIndex); i < array.length; i++) {
      if (Objects.equals(array[i], objectToFind)) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * <p>Checks if the object is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array the array to search through
   * @param objectToFind the object to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(final Object[] array, final Object objectToFind) {
    return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
  }


  /**
   * <p>Checks if an array of Objects is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final Object[] array) {
    return getLength(array) == 0;
  }

  /**
   * <p>Checks if an array of primitive longs is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final long[] array) {
    return getLength(array) == 0;
  }

  /**
   * <p>Checks if an array of primitive ints is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final int[] array) {
    return getLength(array) == 0;
  }

  /**
   * <p>Checks if an array of primitive shorts is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final short[] array) {
    return getLength(array) == 0;
  }

  /**
   * <p>Checks if an array of primitive chars is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final char[] array) {
    return getLength(array) == 0;
  }

  /**
   * <p>Checks if an array of primitive bytes is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final byte[] array) {
    return getLength(array) == 0;
  }

  /**
   * <p>Checks if an array of primitive doubles is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final double[] array) {
    return getLength(array) == 0;
  }

  /**
   * <p>Checks if an array of primitive floats is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final float[] array) {
    return getLength(array) == 0;
  }

  /**
   * <p>Checks if an array of primitive booleans is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final boolean[] array) {
    return getLength(array) == 0;
  }

  // ----------------------------------------------------------------------

  /**
   * 判断是否完全包含
   *
   * @param sources 源数组
   * @param targets 目标数组
   * @return 源数组是否包含目标数组
   */
  public static boolean contains(Object[] sources, Object[] targets) {
    if (Objects.isNull(sources)) {
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

  /**
   * 获取一个目标类型的子类值
   *
   * @param array 源数组
   * @param cls 父类
   * @param <T> 父类类型
   * @return 目标类型的子类
   */
  @SuppressWarnings("unchecked")
  public static <T> T findValueForParent(Object[] array, Class<T> cls) {
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
}
