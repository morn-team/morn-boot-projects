package site.morn.task;

import java.util.concurrent.Callable;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * 异步任务发布者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/24
 */
public interface ListenableFuturePublisher {

  /**
   * 发布任务
   *
   * @param task 任务
   * @param <V> 结果类型
   * @return 异步结果
   */
  <V> ListenableFuture<V> submit(Callable<V> task);

  /**
   * 发布任务
   *
   * @param task 任务
   * @param <V> 结果类型
   * @return 异步结果
   */
  <V> ListenableFuture<V> submit(Runnable task);
}
