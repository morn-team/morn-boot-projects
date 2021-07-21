package site.morn.task;

import java.util.concurrent.Callable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 任务组元素
 *
 * <p>代表任务组中的一个子任务</p>
 *
 * @param <T> 结果类型
 * @author timely-rain
 * @since 1.2.0, 2019/7/29
 */
@RequiredArgsConstructor
public class TaskGroupElement<T> implements Callable<T> {

  /**
   * 任务元素在整个任务中的索引
   */
  @Getter
  private final int index;

  /**
   * 任务执行体
   */
  private final Callable<T> callable;

  @Override
  public T call() throws Exception {
    return callable.call();
  }
}
