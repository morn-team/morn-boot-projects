package site.morn.task;

import java.util.concurrent.Callable;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import site.morn.bean.annotation.Objective;

/**
 * 基础任务发布者
 *
 * @author timely-rain
 * @since 2.1.0, 2019/7/25
 */
@Objective
public class SimpleListenableFuturePublisher implements ListenableFuturePublisher {

  private final AsyncListenableTaskExecutor taskExecutor;

  public SimpleListenableFuturePublisher(AsyncListenableTaskExecutor taskExecutor) {
    this.taskExecutor = taskExecutor;
  }

  @Override
  public <V> ListenableFuture<V> submit(Callable<V> task) {
    return taskExecutor.submitListenable(task);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <V> ListenableFuture<V> submit(Runnable task) {
    return (ListenableFuture<V>) taskExecutor.submitListenable(task);
  }
}
