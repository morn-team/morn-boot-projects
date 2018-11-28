package site.morn.boot.bean;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import site.morn.bean.BeanIdentify;
import site.morn.bean.BeanIdentify.BeanIdentifyBuilder;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.bean.IdentifiedBeanHolder;
import site.morn.bean.annotation.Name;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;

/**
 * 标识实例后置处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/28
 */
@Slf4j
public class IdentifiedBeanPostProcessor implements BeanPostProcessor {

  /**
   * 标识实例缓存
   */
  private final IdentifiedBeanCache identifiedBeanCache;

  public IdentifiedBeanPostProcessor(IdentifiedBeanCache identifiedBeanCache) {
    this.identifiedBeanCache = identifiedBeanCache;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    // 获取所有实例标识注解
    Name name = AnnotationUtils.findAnnotation(bean.getClass(), Name.class);
    Tag tag = AnnotationUtils.findAnnotation(bean.getClass(), Tag.class);
    Target target = AnnotationUtils.findAnnotation(bean.getClass(), Target.class);
    log.info(beanName);
    if (Objects.isNull(name) && Objects.isNull(tag) && Objects.isNull(target)) {
      return bean;
    }
    // 构建实例标识信息
    BeanIdentifyBuilder identifyBuilder = BeanIdentify.builder();
    if (Objects.nonNull(name)) {
      identifyBuilder.name(name.value());
    }
    if (Objects.nonNull(tag)) {
      identifyBuilder.tags(tag.value());
    }
    if (Objects.nonNull(target)) {
      identifyBuilder.target(target.value());
    }
    // 构建标识实例持有者
    IdentifiedBeanHolder<Object> beanHolder = new IdentifiedBeanHolder<>();
    beanHolder.setIdentify(identifyBuilder.build());
    beanHolder.setBean(bean);
    // 缓存实例
    identifiedBeanCache.register(beanHolder);
    return bean;
  }
}
