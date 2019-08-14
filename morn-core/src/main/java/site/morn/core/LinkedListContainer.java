package site.morn.core;

import java.util.Collection;
import java.util.LinkedList;

/**
 * LinkedList容器
 *
 * @author timely-rain
 * @since 2.1.0, 2019/7/25
 */
public class LinkedListContainer<T> extends LinkedList<T> implements CollectionContainer<T> {

  public LinkedListContainer() {
  }

  public LinkedListContainer(Collection<? extends T> c) {
    super(c);
  }
}
