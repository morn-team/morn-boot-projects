package site.morn.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步工具类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/30
 */
@Slf4j
@UtilityClass
public class AsyncUtils {

  /**
   * 等待
   *
   * @param latch 线程计数锁
   */
  public static void await(CountDownLatch latch) {
    try {
      latch.await();
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    }
  }

  /**
   * 获取异步结果
   *
   * @param future 异步操作
   * @param <T> 结果类型
   * @return 操作结果
   */
  public static <T> T getFutureResult(Future<T> future) {
    return getFutureResult(future, null);
  }

  /**
   * 获取异步操作结果
   *
   * @param future 异步操作
   * @param defaultValue 缺省值
   * @param <T> 结果类型
   * @return 操作结果
   */
  public static <T> T getFutureResult(Future<T> future, T defaultValue) {
    try {
      return future.get();
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    } catch (ExecutionException e) {
      log.error(e.getMessage(), e);
    }
    return defaultValue;
  }
}
