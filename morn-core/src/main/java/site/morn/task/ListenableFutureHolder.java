package site.morn.task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;
import site.morn.constant.ApplicationConstant.Errors;
import site.morn.exception.ApplicationMessages;

/**
 * 异步任务持有者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/29
 */
@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class ListenableFutureHolder<T> {

  /**
   * 索引
   */
  private int index;

  /**
   * 异步任务
   */
  private ListenableFuture<T> future;

  /**
   * 获取执行结果
   */
  public T getResult() {
    if (!isDone()) {
      throw ApplicationMessages.translateException(Errors.TASK_NOT_DONE, "[Task]异步任务尚未完成.");
    }
    try {
      return get();
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    } catch (ExecutionException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public void addCallback(
      ListenableFutureCallback<? super T> callback) {
    future.addCallback(callback);
  }

  public void addCallback(
      SuccessCallback<? super T> successCallback,
      FailureCallback failureCallback) {
    future.addCallback(successCallback, failureCallback);
  }

  public CompletableFuture<T> completable() {
    return future.completable();
  }

  public boolean cancel(boolean mayInterruptIfRunning) {
    return future.cancel(mayInterruptIfRunning);
  }

  public boolean isCancelled() {
    return future.isCancelled();
  }

  public boolean isDone() {
    return future.isDone();
  }

  public T get() throws InterruptedException, ExecutionException {
    return future.get();
  }

  public T get(long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return future.get(timeout, unit);
  }
}
