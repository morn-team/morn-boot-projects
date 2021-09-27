package site.morn.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.exception.ApplicationMessages;
import site.morn.task.TaskExecutors;

/**
 * 异步工具类单元测试
 *
 * @author timely-rain
 * @since 1.2.0, 2021/7/20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AsyncUtilsTest {

  @Test
  public void await1() {
    CountDownLatch latch = new CountDownLatch(1);
    TaskExecutors.submit(latch::countDown);
    try {
      AsyncUtils.await(latch);
    } catch (Exception ignore) {
      Assert.fail();
    }
  }

  @Test
  public void getFutureResult1() {
    CompletableFuture<String> future = TaskExecutors.submit(() -> "This is result.");
    String result = AsyncUtils.getFutureResult(future);
    Assert.assertEquals("This is result.", result);
  }

  @Test
  public void getFutureResult2() {
    CompletableFuture<Object> future = TaskExecutors.submit(() -> {
      throw ApplicationMessages.buildException("This is Exception.");
    });
    Object result = AsyncUtils.getFutureResult(future);
    Assert.assertNull(result);
  }
}