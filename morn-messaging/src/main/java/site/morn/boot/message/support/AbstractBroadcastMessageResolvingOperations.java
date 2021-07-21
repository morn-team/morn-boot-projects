package site.morn.boot.message.support;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import site.morn.boot.message.BroadcastMessageHeaderResolver;
import site.morn.boot.message.BroadcastMessageResolvingOperations;
import site.morn.task.TaskExecutors;

/**
 * 抽象消息解析操作类
 *
 * @param <T> 消息类型
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
@Getter
public abstract class AbstractBroadcastMessageResolvingOperations<T> implements
    BroadcastMessageResolvingOperations<T> {

  private final AnnotationBroadcastMessageHandler<T> messageHandler;

  private final ConfigurableConversionService conversionService;

  protected AbstractBroadcastMessageResolvingOperations(
      AnnotationBroadcastMessageHandler<T> messageHandler,
      List<BroadcastMessageHeaderResolver<?>> headerResolvers) {
    this.messageHandler = messageHandler;
    // 构建类型转换服务
    conversionService = new GenericConversionService();
    for (BroadcastMessageHeaderResolver<?> headerResolver : headerResolvers) {
      conversionService.addConverter(headerResolver);
    }
    // 为消息处理者注入类型转换服务
    messageHandler.setConversionService(conversionService);
  }

  @Override
  public CompletableFuture<Boolean> asyncResolve(T message) {
    return TaskExecutors.submit(() -> syncResolve(message));
  }
}
