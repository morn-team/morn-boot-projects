package site.morn.task;

import java.util.List;
import java.util.concurrent.Callable;
import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;
import site.morn.bean.support.BeanCaches;

/**
 * 异步任务调度者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/24
 */
@UtilityClass
public class ListenableFutureDispatcher {

  /**
   * 发布任务
   *
   * @param task 任务
   * @param <V> 结果类型
   * @return 异步结果
   */
  public static <V> ListenableFuture<V> submit(Runnable task) {
    ListenableFuturePublisher publisher = BeanCaches.bean(ListenableFuturePublisher.class);
    Assert.notNull(publisher, "尚未注册任务发布者");
    return publisher.submit(task);
  }

  /**
   * 发布任务
   *
   * @param task 任务
   * @param <V> 结果类型
   * @return 异步结果
   */
  public static <V> ListenableFuture<V> submit(Callable<V> task) {
    ListenableFuturePublisher publisher = BeanCaches.bean(ListenableFuturePublisher.class);
    Assert.notNull(publisher, "尚未注册任务发布者");
    return publisher.submit(task);
  }

  /**
   * 发布任务
   *
   * @param tasks 任务集合
   * @param <V> 结果类型
   * @return 任务组
   */
  public static <V> ListenableFutureGroup<V> submit(List<Callable<V>> tasks) {
    ListenableFutureGroup<V> futureGroup = ListenableFutureGroup.build();
    for (Callable<V> task : tasks) {
      futureGroup.addTask(task);
    }
    ListenableFuturePublisher dispatcher = BeanCaches.bean(ListenableFuturePublisher.class);
    dispatcher.submit(futureGroup);
    return futureGroup;
  }
}
