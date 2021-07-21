package site.morn.task;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.exception.ApplicationMessages;

/**
 * 任务调度单元测试
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/25
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskExecutorsTest {

  private static final int CYCLE = 1000;

  /**
   * 执行计数
   */
  private final AtomicInteger EXECUTED_COUNT = new AtomicInteger();

  /**
   * 完成计数
   */
  private final AtomicInteger COMPLETE_COUNT = new AtomicInteger();

  /**
   * 成功计数
   */
  private final AtomicInteger SUCCESS_COUNT = new AtomicInteger();

  /**
   * 失败计数
   */
  private final AtomicInteger FAILURE_COUNT = new AtomicInteger();

  @Test
  public void submit1() {
    CompletableFuture<String> future = TaskExecutors.submit(this::call);
    thenSetCount(future);
    await().timeout(Duration.FIVE_SECONDS).untilAtomic(EXECUTED_COUNT, equalTo(1));
    await().timeout(Duration.ONE_SECOND).untilAtomic(COMPLETE_COUNT, equalTo(1));
    await().timeout(Duration.ONE_SECOND).untilAtomic(SUCCESS_COUNT, equalTo(1));
    await().timeout(Duration.ONE_SECOND).untilAtomic(FAILURE_COUNT, equalTo(0));
    Assert.assertEquals(1, EXECUTED_COUNT.get());
  }

  @Test
  public void submits2() {
    for (int i = 0; i < CYCLE; i++) {
      CompletableFuture<String> future = TaskExecutors.submit(this::call);
      thenSetCount(future);
    }
    await().timeout(Duration.TWO_MINUTES).untilAtomic(EXECUTED_COUNT, equalTo(CYCLE));
    await().timeout(Duration.ONE_SECOND).untilAtomic(COMPLETE_COUNT, equalTo(CYCLE));
    await().timeout(Duration.ONE_SECOND).untilAtomic(SUCCESS_COUNT, equalTo(CYCLE));
    await().timeout(Duration.ONE_SECOND).untilAtomic(FAILURE_COUNT, equalTo(0));
    Assert.assertEquals(CYCLE, EXECUTED_COUNT.get());
  }

  @Test
  public void submits3() {
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    for (int i = 0; i < CYCLE; i++) {
      executorService.submit(() -> {
        CompletableFuture<String> future = TaskExecutors.submit(this::call);
        thenSetCount(future);
        return future;
      });
    }
    await().timeout(Duration.TWO_MINUTES).untilAtomic(EXECUTED_COUNT, equalTo(CYCLE));
    await().timeout(Duration.ONE_SECOND).untilAtomic(COMPLETE_COUNT, equalTo(CYCLE));
    await().timeout(Duration.ONE_SECOND).untilAtomic(SUCCESS_COUNT, equalTo(CYCLE));
    await().timeout(Duration.ONE_SECOND).untilAtomic(FAILURE_COUNT, equalTo(0));
    executorService.shutdown();

    Assert.assertEquals(CYCLE, EXECUTED_COUNT.get());
  }

  @Test
  public void group1() {
    TaskGroupExecutor<String> groupExecutor = TaskExecutors.groupExecutor(CYCLE);
    for (int i = 0; i < CYCLE; i++) {
      CompletableFuture<String> future;
      if (i % 100 == 0) {
        future = groupExecutor.submit(i, this::callExcept);
      } else {
        future = groupExecutor.submit(i, this::call);
      }
      thenSetCount(future);
    }
    TaskGroupFuture<String> taskGroupFuture = groupExecutor.submitted();
    CompletableFuture<Void> future = taskGroupFuture.allOf();
    thenSetCount(future);
    // 校验回调数量
    await().timeout(Duration.TWO_MINUTES).untilAtomic(EXECUTED_COUNT, equalTo(CYCLE - 10));
    await().timeout(Duration.ONE_SECOND).untilAtomic(COMPLETE_COUNT, equalTo(CYCLE + 1));
    await().timeout(Duration.ONE_SECOND).untilAtomic(SUCCESS_COUNT, equalTo(CYCLE - 10));
    await().timeout(Duration.ONE_SECOND).untilAtomic(FAILURE_COUNT, equalTo(10 + 1));

    // 校验记录结果数量
    List<CompletableFuture<String>> elements = taskGroupFuture.elements();
    List<String> results = taskGroupFuture.results();
    List<Throwable> throwableList = taskGroupFuture.throwableList();
    List<Integer> successIndexes = taskGroupFuture.successIndexes();
    List<Integer> failureIndexes = taskGroupFuture.failureIndexes();
    Assert.assertEquals(CYCLE, elements.size());
    Assert.assertEquals(CYCLE, results.size());
    Assert.assertEquals(10, throwableList.size());
    Assert.assertEquals(CYCLE - 10, successIndexes.size());
    Assert.assertEquals(10, failureIndexes.size());
  }

  /**
   * 正常任务
   */
  private String call() {
    long begin = System.currentTimeMillis();
    String s = "Done" + EXECUTED_COUNT.addAndGet(1);
    await().pollDelay(Duration.ONE_HUNDRED_MILLISECONDS).until(() -> true);
    log.info("{}任务耗时：{}", Thread.currentThread().getName(), System.currentTimeMillis() - begin);
    return s;
  }

  /**
   * 异常任务
   */
  private String callExcept() {
    long begin = System.currentTimeMillis();
    await().pollDelay(Duration.ONE_HUNDRED_MILLISECONDS).until(() -> true);
    log.info("{}异常耗时：{}", Thread.currentThread().getName(), System.currentTimeMillis() - begin);
    throw ApplicationMessages.buildException("异常任务");
  }

  /**
   * 计数
   */
  private <T> void thenSetCount(CompletableFuture<T> future) {
    future.thenAccept(s -> SUCCESS_COUNT.addAndGet(1)) // 成功计数+1
        .exceptionally(throwable -> {
          FAILURE_COUNT.addAndGet(1); // 失败计数+1
          return null;
        })
        .whenComplete((unused, throwable) -> COMPLETE_COUNT.addAndGet(1)); // 完成计数+1
  }
}