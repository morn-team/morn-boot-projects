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

  /**
   * 生成实例持有者
   *
   * @param bean 实例
   * @param <T> 实例类型
   * @return 实例持有者
   */
  public static <T> IdentifiedBeanHolder<T> generateBeanHolder(T bean) {
    // 获取所有实例标识注解
    Class<?> beanClass = bean.getClass();
    Name name = AnnotationUtils.findAnnotation(beanClass, Name.class);
    Tag tag = AnnotationUtils.findAnnotation(beanClass, Tag.class);
    Target target = AnnotationUtils.findAnnotation(beanClass, Target.class);
    if (Objects.isNull(name) && Objects.isNull(tag) && Objects.isNull(target)) {
      return null;
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
    IdentifiedBeanHolder<T> beanHolder = new IdentifiedBeanHolder<>();
    beanHolder.setIdentify(identifyBuilder.build());
    beanHolder.setBean(bean);
    return beanHolder;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    // 获取所有实例标识注解
    IdentifiedBeanHolder<Object> beanHolder = generateBeanHolder(bean);
    if (Objects.isNull(beanHolder)) {
      return bean;
    }
    log.info("注册标识实例：" + beanName);
    // 缓存实例
    identifiedBeanCache.register(beanHolder);
    return bean;
  }
}
