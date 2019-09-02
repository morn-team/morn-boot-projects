package site.morn.boot.autoconfigure;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import site.morn.bean.BeanAnnotation;
import site.morn.bean.BeanAnnotationRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.boot.bean.BeanCacheInitializer;
import site.morn.boot.bean.IdentifiedBeanPostProcessor;
import site.morn.boot.bean.SimpleIdentifiedBeanCache;

/**
 * 实例自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/28
 */
@Configuration
@ConditionalOnClass(CacheManager.class)
public class BeanAutoConfiguration {

  /**
   * 注册实例注解注册表
   *
   * @return 实例注解注册表
   */
  @Bean
  @ConditionalOnMissingBean
  public BeanAnnotationRegistry beanAnnotationRegistry() {
    return new BeanAnnotationRegistry();
  }

  /**
   * 注册标识实例缓存
   *
   * <p>提供实例缓存功能
   *
   * @return 标识实例缓存
   */
  @Bean
  @ConditionalOnMissingBean
  public IdentifiedBeanCache identifiedBeanCache() {
    return new SimpleIdentifiedBeanCache();
  }

  /**
   * 注册标识实例后置处理器
   *
   * <p>识别实例注解并缓存
   *
   * @param configurers 实例配置
   * @param registry 实例注解注册表
   * @param identifiedBeanCache 标识实例缓存
   * @return 标识实例后置处理器
   */
  @Bean
  @ConditionalOnMissingBean
  public IdentifiedBeanPostProcessor identifiedBeanPostProcessor(List<BeanConfigurer> configurers,
      BeanAnnotationRegistry registry, IdentifiedBeanCache identifiedBeanCache) {
    initBeanAnnotation(configurers, registry);
    return new IdentifiedBeanPostProcessor(registry, identifiedBeanCache);
  }

  /**
   * 注册实例缓存初始化器
   *
   * @param identifiedBeanCache 实例缓存
   * @return 实例缓存初始化器
   */
  @Bean
  @ConditionalOnMissingBean
  public BeanCacheInitializer beanCacheInitializer(IdentifiedBeanCache identifiedBeanCache) {
    return new BeanCacheInitializer(identifiedBeanCache);
  }

  /**
   * 初始化实例注解
   *
   * @param configurers 实例配置
   * @param registry 实例注解注册表
   */
  private void initBeanAnnotation(List<BeanConfigurer> configurers,
      BeanAnnotationRegistry registry) {
    // 注册自定义注解实例
    for (BeanConfigurer configurer : configurers) {
      configurer.addBeanAnnotations(registry);
    }
    // 设置默认值
    for (BeanAnnotation beanAnnotation : registry.getAnnotations()) {
      if (StringUtils.isEmpty(beanAnnotation.getTagName())) {
        String name = StringUtils.uncapitalize(beanAnnotation.getAnnotationType().getSimpleName());
        beanAnnotation.setTagName(name); // 默认标签名称
      }
      if (StringUtils.isEmpty(beanAnnotation.getAttributeName())) {
        beanAnnotation.setAttributeName(AnnotationUtils.VALUE); // 默认属性名称
      }
    }
  }
}
