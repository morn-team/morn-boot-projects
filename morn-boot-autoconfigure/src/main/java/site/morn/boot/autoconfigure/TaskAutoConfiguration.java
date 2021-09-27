package site.morn.boot.autoconfigure;

import static org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import site.morn.task.CompletableTaskExecutor;
import site.morn.task.SimpleCompletableTaskExecutor;

/**
 * 异步任务自动化配置
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/25
 */
@Configuration
@ConditionalOnBean(TaskExecutorBuilder.class)
public class TaskAutoConfiguration {

  /**
   * 注册异步任务发布者
   */
  @Bean
  @ConditionalOnMissingBean
  public CompletableTaskExecutor taskPublisher(
      @Qualifier(APPLICATION_TASK_EXECUTOR_BEAN_NAME) AsyncListenableTaskExecutor taskExecutor) {
    return new SimpleCompletableTaskExecutor(taskExecutor);
  }
}
