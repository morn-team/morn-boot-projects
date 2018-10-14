package site.morn.boot.exception;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import site.timely.exception.ExceptionResolver;
import site.timely.exception.ExceptionResolverCache;
import site.timely.exception.SimpleAnnotationExceptionResolver;
import site.timely.tag.annotation.Tag;

/**
 * 异常解析监听器
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/20
 * @since 1.0
 */
@RequiredArgsConstructor
public class ExceptionResolverListener {

  private final ExceptionResolverCache cache;

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
    ConfigurableApplicationContext context = event.getApplicationContext();
    Map<String, ExceptionResolver> resolverMap = context.getBeansOfType(ExceptionResolver.class);
    for (ExceptionResolver resolver : resolverMap.values()) {
      Class<? extends ExceptionResolver> resolverClass = resolver.getClass();
      Tag tag = AnnotationUtils.findAnnotation(resolverClass, Tag.class);
      SimpleAnnotationExceptionResolver annotationExceptionResolver = new SimpleAnnotationExceptionResolver();
      annotationExceptionResolver.setTags(tag.tags());
      annotationExceptionResolver.setTargets(tag.targets());
      annotationExceptionResolver.setResolver(resolver);
      cache.put(annotationExceptionResolver);
    }
  }

  @EventListener
  public void ready(ApplicationReadyEvent event) {
  }
}
