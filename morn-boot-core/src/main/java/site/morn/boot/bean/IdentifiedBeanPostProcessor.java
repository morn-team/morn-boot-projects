package site.morn.boot.bean;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import site.morn.bean.BeanAnnotationRegistry;
import site.morn.bean.BeanCache;
import site.morn.bean.IdentifiedBeanHolder;
import site.morn.boot.util.BeanCacheUtils;

/**
 * 标识实例后置处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/28
 */
@Slf4j
public class IdentifiedBeanPostProcessor implements BeanPostProcessor {

  /**
   * 实例注解注册表
   */
  private final BeanAnnotationRegistry registry;

  /**
   * 标识实例缓存
   */
  private final BeanCache beanCache;

  public IdentifiedBeanPostProcessor(BeanAnnotationRegistry registry,
      BeanCache beanCache) {
    this.registry = registry;
    this.beanCache = beanCache;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    // 获取所有实例标识注解
    IdentifiedBeanHolder<Object> beanHolder = BeanCacheUtils.generateBeanHolder(bean, registry);
    if (Objects.isNull(beanHolder)) {
      return bean;
    }
    log.info("注册标识实例：{}", beanName);
    // 缓存实例
    beanCache.cache(beanHolder);
    return bean;
  }
}
