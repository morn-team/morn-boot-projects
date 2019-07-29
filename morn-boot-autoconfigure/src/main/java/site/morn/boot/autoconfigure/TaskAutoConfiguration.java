package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import site.morn.task.ListenableFuturePublisher;
import site.morn.task.SimpleListenableFuturePublisher;

/**
 * 异步任务自动化配置
 *
 * @author timely-rain
 * @since 2.1.0, 2019/7/25
 */
@Configuration
public class TaskAutoConfiguration {

  /**
   * 注册任务发布者
   */
  @Bean
  @ConditionalOnMissingBean
  public ListenableFuturePublisher taskPublisher(AsyncListenableTaskExecutor taskExecutor) {
    return new SimpleListenableFuturePublisher(taskExecutor);
  }
}
