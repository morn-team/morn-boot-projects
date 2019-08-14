package site.morn.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import lombok.experimental.UtilityClass;
import site.morn.core.ArrayListContainer;
import site.morn.core.CollectionContainer;
import site.morn.core.LinkedListContainer;

/**
 * 集合容器工具类
 *
 * @author timely-rain
 * @since 2.1.0, 2019/7/25
 */
@UtilityClass
public class CollectionContainerUtils {

  /**
   * Collection to ArrayListContainer
   */
  public static <T> ArrayListContainer<T> toArrayListContainer(Collection<T> collection) {
    if (collection instanceof ArrayListContainer) {
      return (ArrayListContainer<T>) collection;
    }
    return new ArrayListContainer<>(collection);
  }

  /**
   * Collection to ArrayListContainer
   */
  public static <T> LinkedListContainer<T> toLinkedListContainer(Collection<T> collection) {
    if (collection instanceof LinkedListContainer) {
      return (LinkedListContainer<T>) collection;
    }
    return new LinkedListContainer<>(collection);
  }

  /**
   * Collection to CollectionContainer
   */
  public static <T> CollectionContainer<T> toCollectionContainer(Collection<T> collection) {
    if (collection instanceof ArrayList) {
      return toArrayListContainer(collection);
    }
    if (collection instanceof LinkedList) {
      return toLinkedListContainer(collection);
    }
    return toArrayListContainer(collection);
  }
}
