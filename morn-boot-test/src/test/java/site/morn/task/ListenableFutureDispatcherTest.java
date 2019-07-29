package site.morn.task;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import java.util.concurrent.Callable;
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
 * @since 2.1.0, 2019/7/25
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ListenableFutureDispatcherTest {

  private AtomicInteger count = new AtomicInteger();

  @Test
  public void submit() {
    Callable<String> callable = () -> ListenableFutureDispatcher.submit(this::call).get();
    await().until(callable, equalTo("Done"));
  }

  @Test
  public void submits() {
    ListenableFutureGroup<String> listenableFutureGroup = ListenableFutureGroup.build();
    for (int i = 0; i < 10; i++) {
      listenableFutureGroup.addTask(this::call);
    }
    listenableFutureGroup.run();
    Matcher hasSize = hasSize(10);
    Matcher everyItem = everyItem(not("Failure"));
    await().until(listenableFutureGroup::getAll, allOf(hasSize, everyItem));
  }

  /**
   * 测试任务
   */
  private String call() {
    long begin = System.currentTimeMillis();
    try {
      String s = "Done" + count.addAndGet(1);
      Thread.sleep(1000);
      log.info("{}任务耗时：{}", Thread.currentThread().getName(), System.currentTimeMillis() - begin);
      return s;
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      return "Failure";
    }
  }
}