package site.morn.boot.bean;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import site.morn.bean.BeanCache;
import site.morn.bean.BeanPool;
import site.morn.bean.FunctionPool;
import site.morn.bean.support.BeanCaches;

/**
 * 实例缓存初始化
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/10
 */
@RequiredArgsConstructor
public class BeanCacheInitializer implements ApplicationContextAware {

  /**
   * 实例缓存
   */
  private final BeanCache beanCache;

  /**
   * 默认实例池
   */
  private final BeanPool beanPool;

  /**
   * 默认函数池
   */
  private final FunctionPool functionPool;

  /**
   * 实例工厂
   */
  private BeanFactory beanFactory;

  @PostConstruct
  public void initialize() {
    BeanCaches.initialize(beanCache, beanPool, functionPool, beanFactory);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.beanFactory = applicationContext;
  }
}
