package site.morn.core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ArrayList容器
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/25
 */
public class ArrayListContainer<T> extends ArrayList<T> implements CollectionContainer<T> {

  public ArrayListContainer(int initialCapacity) {
    super(initialCapacity);
  }

  public ArrayListContainer() {
  }

  public ArrayListContainer(Collection<? extends T> c) {
    super(c);
  }
}
