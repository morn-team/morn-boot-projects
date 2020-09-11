package site.morn.task;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 任务调度单元测试
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/25
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ListenableFutureDispatcherTest {

  private static final int CYCLE = 1000;

  private static final String FAILURE = "Failure";

  private final AtomicInteger COUNT = new AtomicInteger();

  @Test
  public void submit() {
    ListenableFutureDispatcher.submit(this::call);
    await().timeout(1, TimeUnit.MINUTES).untilAtomic(COUNT, equalTo(1));
  }

  @Test
  public void submits() {
    for (int i = 0; i < CYCLE; i++) {
      ListenableFutureDispatcher.submit(this::call);
    }
    await().timeout(2, TimeUnit.MINUTES).untilAtomic(COUNT, equalTo(CYCLE));
  }

  @Test
  public void asyncSubmits() {
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    for (int i = 0; i < CYCLE; i++) {
      executorService.submit(() -> ListenableFutureDispatcher.submit(this::call));
    }
    await().timeout(2, TimeUnit.MINUTES).untilAtomic(COUNT, equalTo(CYCLE));
    executorService.shutdown();
  }

  @Test
  public void group() {
    ListenableFutureGroup<String> listenableFutureGroup = ListenableFutureGroup.build();
    for (int i = 0; i < CYCLE; i++) {
      listenableFutureGroup.addTask(this::call);
    }
    listenableFutureGroup.run();
    Matcher hasSize = hasSize(CYCLE);
    Matcher everyItem = everyItem(not(FAILURE));
    await().timeout(2, TimeUnit.MINUTES)
        .until(listenableFutureGroup::getAll, allOf(hasSize, everyItem));
  }

  @Test
  public void asyncGroup() {
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    for (int i = 0; i < CYCLE / 10; i++) {
      executorService.submit(() -> {
        for (int j = 0; j < 10; j++) {
          ListenableFutureGroup<String> listenableFutureGroup = ListenableFutureGroup.build();
          listenableFutureGroup.addTask(this::call);
          listenableFutureGroup.run();
        }
      });
    }
    await().timeout(2, TimeUnit.MINUTES).untilAtomic(COUNT, equalTo(CYCLE));
    executorService.shutdown();
  }

  /**
   * 测试任务
   */
  private String call() {
    long begin = System.currentTimeMillis();
    try {
      String s = "Done" + COUNT.addAndGet(1);
      Thread.sleep(100);
      log.info("{}任务耗时：{}", Thread.currentThread().getName(), System.currentTimeMillis() - begin);
      return s;
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    }
    return FAILURE;
  }
}