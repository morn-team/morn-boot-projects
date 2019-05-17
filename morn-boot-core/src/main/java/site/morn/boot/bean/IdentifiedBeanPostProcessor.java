package site.morn.boot.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import site.morn.bean.BeanIdentify;
import site.morn.bean.BeanIdentify.BeanIdentifyBuilder;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.bean.IdentifiedBeanHolder;
import site.morn.bean.MethodHolder;
import site.morn.bean.annotation.Function;
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

  /**
   * 生成实例持有者
   *
   * @param bean 实例
   * @param <T> 实例类型
   * @return 实例持有者
   */
  static <T> IdentifiedBeanHolder<T> generateBeanHolder(T bean) {
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

    List<MethodHolder> annotationMethods = getAnnotationMethods(beanClass);

    // 构建标识实例持有者
    return new IdentifiedBeanHolder<>(identifyBuilder.build(), bean, annotationMethods);
  }

  /**
   * 获取注解方法
   *
   * @param beanClass 实例类
   * @return 注解方法
   */
  private static List<MethodHolder> getAnnotationMethods(Class<?> beanClass) {
    List<MethodHolder> methodHolders = new ArrayList<>();

    for (Method method : beanClass.getMethods()) {
      Function function = AnnotationUtils.findAnnotation(method, Function.class);
      if (Objects.isNull(function)) {
        continue;
      }
      String functionName = function.value();
      if (contains(methodHolders, functionName)) {
        log.warn("函数重复注入：{}.{}#{}", beanClass.getSimpleName(), method.getName(),
            functionName);
        continue;
      }
      MethodHolder methodHolder = new MethodHolder(functionName, null, method,
          method.getParameterTypes());
      methodHolders.add(methodHolder);
      log.warn("注册缓存函数：{}.{}#{}", beanClass.getSimpleName(), method.getName(),
          functionName);
    }
    return methodHolders;
  }


  /**
   * 判断函数名称是否重复
   *
   * @param methodHolders 方法持有者集合
   * @param functionName 函数名称
   * @return 函数名称重复
   */
  private static boolean contains(List<MethodHolder> methodHolders, String functionName) {
    return methodHolders.stream()
        .anyMatch(methodHolder -> Objects.equals(methodHolder.getName(), functionName));
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    // 获取所有实例标识注解
    IdentifiedBeanHolder<Object> beanHolder = generateBeanHolder(bean);
    if (Objects.isNull(beanHolder)) {
      return bean;
    }
    log.info("注册标识实例：" + beanName);
    // 缓存实例
    identifiedBeanCache.cache(beanHolder);
    return bean;
  }
}
