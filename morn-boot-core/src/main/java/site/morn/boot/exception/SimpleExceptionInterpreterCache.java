package site.morn.boot.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import site.morn.exception.AnnotationExceptionInterpreterHolder;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionInterpreterCache;

/**
 * 默认异常处理器的缓存器
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/21
 * @since 1.0
 */
public class SimpleExceptionInterpreterCache implements ExceptionInterpreterCache {

  /**
   * 仓库
   */
  private List<AnnotationExceptionInterpreterHolder> store = new ArrayList<>();

  @Cacheable(value = DEFAULT_CACHE, key = "'TAG.'+T(org.springframework.util.StringUtils).arrayToCommaDelimitedString(#tag)")
  @Override
  public List<ExceptionInterpreter> find(String tag) {
    return store.stream().filter(resolver -> Arrays.binarySearch(resolver.getTags(), tag) >= 0)
        .map(AnnotationExceptionInterpreterHolder::getExceptionInterpreter)
        .collect(Collectors.toList());
  }

  @Cacheable(value = DEFAULT_CACHE, key = "'CLASS.'+T(org.springframework.util.StringUtils).arrayToCommaDelimitedString(#targets)")
  @Override
  public List<ExceptionInterpreter> find(Class<?>... targets) {
    return store.stream().filter(resolver -> {
      for (Class<?> resolverTarget : resolver.getTargets()) {
        for (Class<?> target : targets) {
          if (target.isAssignableFrom(resolverTarget)) {
            return true;
          }
        }
      }
      return false;
    }).map(AnnotationExceptionInterpreterHolder::getExceptionInterpreter)
        .collect(Collectors.toList());
  }

  @Override
  public void put(AnnotationExceptionInterpreterHolder resolver) {
    store.add(resolver);
  }
}
