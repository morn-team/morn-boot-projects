package site.morn.core;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合容器
 *
 * @author timely-rain
 * @since 2.1.0, 2019/7/25
 */
public interface CollectionContainer<T> extends Collection<T> {

  /**
   * 遍历执行
   *
   * @param consumer 执行函数
   */
  default void consume(Consumer<T> consumer) {
    for (T t : this) {
      consumer.accept(t);
    }
  }

  /**
   * 遍历执行
   *
   * @param function 执行函数
   * @param <V> 返回类型
   * @return 执行结果
   */
  default <V> List<V> map(Function<T, V> function) {
    return this.stream().map(function).collect(Collectors.toList());
  }
}
