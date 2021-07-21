package site.morn.task;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import site.morn.bean.annotation.Objective;

/**
 * 基础任务发布者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/25
 */
@Objective
public class SimpleCompletableTaskExecutor implements CompletableTaskExecutor {

  private final AsyncListenableTaskExecutor taskExecutor;

  public SimpleCompletableTaskExecutor(AsyncListenableTaskExecutor taskExecutor) {
    this.taskExecutor = taskExecutor;
  }

  @SuppressWarnings("unchecked")
  @Override
  public CompletableFuture<Void> submit(Runnable task) {
    return (CompletableFuture<Void>) taskExecutor.submitListenable(task).completable();
  }

  @Override
  public <V> CompletableFuture<V> submit(Callable<V> task) {
    return taskExecutor.submitListenable(task).completable();
  }
}
