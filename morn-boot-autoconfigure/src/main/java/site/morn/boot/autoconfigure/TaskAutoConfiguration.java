package site.morn.boot.autoconfigure;

import static org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import site.morn.task.ListenableFuturePublisher;
import site.morn.task.SimpleListenableFuturePublisher;

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
   * 注册异步任务线程池
   *
   * @apiNote 沿用Spring的主线程池，当Spring未创建主线池时，则主动创建
   */
  @Lazy
  @Bean(name = {APPLICATION_TASK_EXECUTOR_BEAN_NAME,
      AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME})
  @ConditionalOnMissingBean(name = APPLICATION_TASK_EXECUTOR_BEAN_NAME)
  public ThreadPoolTaskExecutor applicationTaskExecutor(TaskExecutorBuilder builder) {
    return builder.build();
  }

  /**
   * 注册异步任务发布者
   */
  @Bean
  @ConditionalOnMissingBean
  public ListenableFuturePublisher taskPublisher(
      @Qualifier(APPLICATION_TASK_EXECUTOR_BEAN_NAME) AsyncListenableTaskExecutor taskExecutor) {
    return new SimpleListenableFuturePublisher(taskExecutor);
  }
}
