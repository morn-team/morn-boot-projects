package site.morn.boot.bean;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import site.morn.bean.BeanCaches;
import site.morn.bean.IdentifiedBeanCache;

/**
 * 实例缓存初始化
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/10
 */
@RequiredArgsConstructor
public class BeanCacheInitializer {

  /**
   * 实例缓存
   */
  private final IdentifiedBeanCache identifiedBeanCache;

  @PostConstruct
  public void initialize() {
    BeanCaches.initialize(identifiedBeanCache);
  }
}
