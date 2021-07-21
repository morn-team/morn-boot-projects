package site.morn.task;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务组执行器
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/25
 */
@Slf4j
public class TaskGroupExecutor<T> {

  /**
   * 异步任务执行器
   */
  private final CompletableTaskExecutor executor;

  /**
   * 任务组异步操作对象
   */
  private final TaskGroupFuture<T> taskGroupFuture;

  public TaskGroupExecutor(CompletableTaskExecutor executor, int size) {
    this.executor = executor;
    this.taskGroupFuture = new TaskGroupFuture<>(size);
  }

  /**
   * 发布异步子任务
   *
   * @param index    任务索引
   * @param callable 任务执行体
   */
  public CompletableFuture<T> submit(int index, Callable<T> callable) {
    TaskGroupElement<T> element = new TaskGroupElement<>(index, callable);
    return submit(element);
  }

  /**
   * 发布异步子任务
   *
   * @param element 任务元素
   */
  public CompletableFuture<T> submit(TaskGroupElement<T> element) {
    CompletableFuture<T> future = executor.submit(element);
    taskGroupFuture.setElementFuture(element, future);
    return future;
  }

  /**
   * 结束发布
   *
   * @return 任务组异步操作对象
   */
  public TaskGroupFuture<T> submitted() {
    return taskGroupFuture;
  }
}
