package site.morn.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureCallbackRegistry;
import org.springframework.util.concurrent.SuccessCallback;
import site.morn.core.CollectionContainer;
import site.morn.util.CollectionContainerUtils;

/**
 * 任务组
 *
 * @author timely-rain
 * @since 2.1.0, 2019/7/25
 */
@Slf4j
public class ListenableFutureGroup<T> implements ListenableFuture<T>, Runnable {

  /**
   * 回调函数
   */
  private final ListenableFutureCallbackRegistry<T> callbacks = new ListenableFutureCallbackRegistry<>();

  /**
   * 执行集合
   */
  private Collection<Callable<T>> callableList;

  /**
   * 线程锁
   */
  private CountDownLatch latch;

  /**
   * 任务持有者集合
   */
  private CollectionContainer<ListenableFutureHolder<T>> futures;

  /**
   * 结果集
   */
  private Map<Object, T> results = new ConcurrentHashMap<>();

  /**
   * 第一个执行结果
   */
  private volatile T firstResult;

  /**
   * 第一个异常
   */
  private volatile Throwable firstException;

  private ListenableFutureGroup() {
    this.callableList = Collections.synchronizedCollection(new ArrayList<>());
  }

  private ListenableFutureGroup(List<Callable<T>> callableList) {
    this.callableList = callableList;
  }

  /**
   * 构建任务组
   */
  public static <T> ListenableFutureGroup<T> build() {
    return new ListenableFutureGroup<>();
  }

  /**
   * 构建任务组
   */
  public static <T> ListenableFutureGroup<T> withTask(Callable<T> callable) {
    ListenableFutureGroup<T> listenableFutureGroup = new ListenableFutureGroup<>();
    listenableFutureGroup.addTask(callable);
    return listenableFutureGroup;
  }

  /**
   * 构建任务组
   */
  public static <T> ListenableFutureGroup<T> withTasks(List<Callable<T>> callableList) {
    return new ListenableFutureGroup<>(callableList);
  }

  public ListenableFutureGroup<T> addTask(Callable<T> callable) {
    this.callableList.add(callable);
    return this;
  }

  public void addTaskCallback(ListenableFutureCallback<? super T> callback) {
    futures.consume(f -> f.addCallback(callback));
  }

  public void addTaskCallback(SuccessCallback<? super T> successCallback,
      FailureCallback failureCallback) {
    futures.consume(f -> f.addCallback(successCallback, failureCallback));
  }

  public List<T> getAll() {
    try {
      latch.await();
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    }
    return futures.stream().map(ListenableFutureHolder::getResult).collect(Collectors.toList());
  }

  @Override
  public void addCallback(ListenableFutureCallback<? super T> callback) {
    this.callbacks.addCallback(callback);
  }

  @Override
  public void addCallback(SuccessCallback<? super T> successCallback,
      FailureCallback failureCallback) {
    this.callbacks.addSuccessCallback(successCallback);
    this.callbacks.addFailureCallback(failureCallback);
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return futures.stream().allMatch(f -> f.cancel(mayInterruptIfRunning));
  }

  @Override
  public boolean isCancelled() {
    return futures.stream().allMatch(ListenableFutureHolder::isCancelled);
  }

  @Override
  public boolean isDone() {
    return futures.stream().allMatch(ListenableFutureHolder::isDone);
  }

  @Override
  public T get() throws InterruptedException, ExecutionException {
    latch.await();
    return firstResult;
  }

  @Override
  public T get(long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    latch.await();
    return firstResult;
  }

  @Override
  public void run() {
    // 构建线程锁
    this.latch = new CountDownLatch(callableList.size());
    // 发布执行
    List<ListenableFuture<T>> futureList = callableList.stream()
        .map(ListenableFutureDispatcher::submit)
        .collect(Collectors.toList());
    // 必要回调
    List<ListenableFutureHolder<T>> holders = IntStream.range(0, callableList.size())
        .mapToObj(i -> handleFuture(i, futureList))
        .collect(Collectors.toList());
    this.futures = CollectionContainerUtils.toCollectionContainer(holders);
  }

  /**
   * 异步任务处理
   *
   * @param i 索引
   * @param futureList 异步任务集合
   * @return 异步任务持有者
   */
  private ListenableFutureHolder<T> handleFuture(int i, List<ListenableFuture<T>> futureList) {
    ListenableFuture<T> future = futureList.get(i);
    future.addCallback(v -> {
      latch.countDown();
      results.put(i, v);
      // 记录第一个结果
      if (Objects.isNull(firstResult)) {
        firstResult = v;
      }
    }, e -> {
      latch.countDown();
      results.put(i, null);
      // 记录第一个异常
      if (Objects.isNull(firstException)) {
        firstException = e;
      }
    });
    return new ListenableFutureHolder<>(i, future);
  }

}
