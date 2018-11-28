package site.morn.boot.exception;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import site.morn.bean.annotation.Tag;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionInterpreterCache;
import site.morn.exception.SimpleAnnotationExceptionInterpreterHolder;

/**
 * 异常解析监听器
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/20
 * @since 1.0
 */
@AllArgsConstructor
public class ExceptionInterpreterListener {

  private final ExceptionInterpreterCache cache;

  @EventListener
  public void starting(ApplicationStartingEvent event) {
  }

  /**
   * 监听应用制备事件
   *
   * @param event 应用制备事件
   */
  @EventListener
  public void prepared(ApplicationPreparedEvent event) {
  }

  @EventListener
  public void ready(ApplicationReadyEvent event) {
    ConfigurableApplicationContext context = event.getApplicationContext();
    Map<String, ExceptionInterpreter> resolverMap = context
        .getBeansOfType(ExceptionInterpreter.class);
    for (ExceptionInterpreter resolver : resolverMap.values()) {
      Class<? extends ExceptionInterpreter> resolverClass = resolver.getClass();
      Tag tag = AnnotationUtils.findAnnotation(resolverClass, Tag.class);
//      Target target = AnnotationUtils.findAnnotation(resolverClass, Target.class);
      SimpleAnnotationExceptionInterpreterHolder annotationExceptionResolver = new SimpleAnnotationExceptionInterpreterHolder();
      annotationExceptionResolver.setTags(tag.tags());
//      annotationExceptionResolver.setTargets(tag.targets());
      annotationExceptionResolver.setResolver(resolver);
      cache.put(annotationExceptionResolver);
    }
  }
}
