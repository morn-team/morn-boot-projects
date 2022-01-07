package site.morn.boot.autoconfigure;

import static site.morn.bean.AnnotationFieldRegistry.BEAN_ANNOTATION_REGISTRY;
import static site.morn.bean.AnnotationFieldRegistry.FUNCTION_ANNOTATION_REGISTRY;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.AnnotationFieldType;
import site.morn.bean.BeanCache;
import site.morn.bean.BeanConfigurer;
import site.morn.bean.BeanPool;
import site.morn.bean.FunctionPool;
import site.morn.bean.annotation.Function;
import site.morn.bean.annotation.Name;
import site.morn.bean.annotation.Source;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;
import site.morn.boot.bean.AnnotationBeanPostProcessor;
import site.morn.boot.bean.BeanCacheInitializer;
import site.morn.boot.bean.SimpleBeanCache;
import site.morn.boot.bean.SimpleBeanPool;
import site.morn.boot.bean.SimpleFunctionPool;
import site.morn.util.OptionalCollection;

/**
 * 实例自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/28
 */
@Configuration
@ConditionalOnClass(CacheManager.class)
public class BeanAutoConfiguration implements BeanConfigurer {

  @Override
  public void addBeanAnnotations(AnnotationFieldRegistry registry) {
    registry.add(Name.class, AnnotationFieldType.NAME);
    registry.add(Tag.class);
    registry.add(Source.class, AnnotationFieldType.SOURCE);
    registry.add(Target.class, AnnotationFieldType.TARGET);
  }

  @Override
  public void addFunctionAnnotations(AnnotationFieldRegistry registry) {
    registry.add(Function.class, AnnotationFieldType.NAME);
    registry.add(Tag.class);
    registry.add(Source.class, AnnotationFieldType.SOURCE);
    registry.add(Target.class, AnnotationFieldType.TARGET);
  }

  /**
   * 注册实例注解注册表
   *
   * @return 实例注解注册表
   */
  @Bean(BEAN_ANNOTATION_REGISTRY)
  @ConditionalOnMissingBean(name = BEAN_ANNOTATION_REGISTRY)
  public AnnotationFieldRegistry beanAnnotationRegistry() {
    return new AnnotationFieldRegistry();
  }

  /**
   * 注册函数注解注册表
   */
  @Bean(FUNCTION_ANNOTATION_REGISTRY)
  @ConditionalOnMissingBean(name = FUNCTION_ANNOTATION_REGISTRY)
  public AnnotationFieldRegistry functionAnnotationRegistry() {
    return new AnnotationFieldRegistry();
  }

  /**
   * 注册实例缓存
   */
  @Bean
  @ConditionalOnMissingBean
  public BeanCache beanCache(ListableBeanFactory beanFactory) {
    return new SimpleBeanCache(beanFactory);
  }

  /**
   * 注册默认实例池
   */
  @Bean
  @ConditionalOnMissingBean
  public BeanPool beanPool(BeanCache beanCache) {
    return new SimpleBeanPool(beanCache);
  }

  /**
   * 注册默认函数池
   */
  @Bean
  @ConditionalOnMissingBean
  public FunctionPool functionPool(BeanPool beanPool) {
    return new SimpleFunctionPool(beanPool);
  }

  /**
   * 注册标识实例后置处理器
   *
   * <p>识别实例注解并缓存
   *
   * @param configurers            实例配置
   * @param beanAnnotationRegistry 实例注解注册表
   * @param beanCache              标识实例缓存
   * @return 标识实例后置处理器
   */
  @Bean
  @ConditionalOnMissingBean
  public AnnotationBeanPostProcessor annotationBeanPostProcessor(
      @Autowired(required = false) List<BeanConfigurer> configurers,
      @Qualifier(BEAN_ANNOTATION_REGISTRY) AnnotationFieldRegistry beanAnnotationRegistry,
      @Qualifier(FUNCTION_ANNOTATION_REGISTRY) AnnotationFieldRegistry functionAnnotationRegistry,
      BeanCache beanCache) {
    Collection<BeanConfigurer> collection = OptionalCollection.ofNullable(configurers)
        .orElse(Collections.emptyList());
    registeredAnnotations(collection, beanAnnotationRegistry, functionAnnotationRegistry);
    return new AnnotationBeanPostProcessor(beanAnnotationRegistry, functionAnnotationRegistry,
        beanCache);
  }

  /**
   * 注册实例缓存初始化器
   *
   * @param beanCache 实例缓存
   * @return 实例缓存初始化器
   */
  @Bean
  @ConditionalOnMissingBean
  public BeanCacheInitializer beanCacheInitializer(BeanCache beanCache, BeanPool beanPool,
      FunctionPool functionPool) {
    return new BeanCacheInitializer(beanCache, beanPool, functionPool);
  }

  /**
   * 初始化实例注解
   *
   * @param configurers      实例配置
   * @param beanRegistry     实例注解注册表
   * @param functionRegistry 函数注解注册表
   */
  private void registeredAnnotations(Collection<BeanConfigurer> configurers,
      AnnotationFieldRegistry beanRegistry, AnnotationFieldRegistry functionRegistry) {
    // 注册自定义注解实例
    for (BeanConfigurer configurer : configurers) {
      configurer.addBeanAnnotations(beanRegistry);
      configurer.addFunctionAnnotations(functionRegistry);
    }
  }
}
