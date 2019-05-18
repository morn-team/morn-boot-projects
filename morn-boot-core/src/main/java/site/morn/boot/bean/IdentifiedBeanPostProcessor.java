package site.morn.boot.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.morn.bean.AnnotationIdentifyCase;
import site.morn.bean.AnnotationIdentifyCase.AnnotationIdentifyCaseBuilder;
import site.morn.bean.BeanAnnotation;
import site.morn.bean.BeanAnnotationRegistry;
import site.morn.bean.FunctionHolder;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.bean.IdentifiedBeanHolder;
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
   * 实例注解注册表
   */
  private final BeanAnnotationRegistry registry;

  /**
   * 标识实例缓存
   */
  private final IdentifiedBeanCache identifiedBeanCache;

  public IdentifiedBeanPostProcessor(BeanAnnotationRegistry registry,
      IdentifiedBeanCache identifiedBeanCache) {
    this.registry = registry;
    this.identifiedBeanCache = identifiedBeanCache;
  }

  /**
   * 判断函数名称是否重复
   *
   * @param functionHolders 方法持有者集合
   * @param functionName 函数名称
   * @return 函数名称重复
   */
  private static boolean contains(List<FunctionHolder> functionHolders, String functionName) {
    return functionHolders.stream()
        .anyMatch(functionHolder -> Objects.equals(functionHolder.getName(), functionName));
  }

  /**
   * 获取注解方法
   *
   * @param beanClass 实例类
   * @return 注解方法
   */
  private static List<FunctionHolder> getAnnotationMethods(Class<?> beanClass) {
    List<FunctionHolder> functionHolders = new ArrayList<>();

    for (Method method : beanClass.getMethods()) {
      Function function = AnnotationUtils.findAnnotation(method, Function.class);
      if (Objects.isNull(function)) {
        continue;
      }
      String functionName = function.value();
      if (contains(functionHolders, functionName)) {
        log.warn("函数重复注入：{}.{}#{}", beanClass.getSimpleName(), method.getName(),
            functionName);
        continue;
      }
      FunctionHolder functionHolder = new FunctionHolder(functionName, null, method,
          method.getParameterTypes());
      functionHolders.add(functionHolder);
      log.warn("注册缓存函数：{}.{}#{}", beanClass.getSimpleName(), method.getName(),
          functionName);
    }
    return functionHolders;
  }

  /**
   * 生成实例持有者
   *
   * @param bean 实例
   * @param <T> 实例类型
   * @return 实例持有者
   */
  <T> IdentifiedBeanHolder<T> generateBeanHolder(T bean) {
    Class<?> beanClass = bean.getClass();

    // 获取标准实例注解
    Name name = AnnotationUtils.findAnnotation(beanClass, Name.class);
    Tag tag = AnnotationUtils.findAnnotation(beanClass, Tag.class);
    Target target = AnnotationUtils.findAnnotation(beanClass, Target.class);
    // 获取自定义注解
    List<BeanAnnotation> beanAnnotations = registry.getAnnotations();
    List<? extends Annotation> annotations = beanAnnotations.stream().map(
        beanAnnotation -> AnnotationUtils
            .findAnnotation(beanClass, beanAnnotation.getAnnotationType())).filter(Objects::nonNull)
        .collect(Collectors.toList());
    if (Objects.isNull(name) && Objects.isNull(tag) && Objects.isNull(target) && CollectionUtils
        .isEmpty(annotations)) {
      return null;
    }

    // 构建实例标识信息
    AnnotationIdentifyCaseBuilder identifyBuilder = AnnotationIdentifyCase.builder();

    if (Objects.nonNull(name)) { // 获取名称
      identifyBuilder.name(name.value());
    }
    if (Objects.nonNull(target)) { // 获取目标
      identifyBuilder.target(target.value());
    }
    List<String> tags = new ArrayList<>();
    if (Objects.nonNull(tag)) { // 获取标签
      tags.addAll(Arrays.asList(tag.value()));
    }
    if (!CollectionUtils.isEmpty(annotations)) { // 获取自定义标签
      for (BeanAnnotation beanAnnotation : beanAnnotations) {
        Annotation annotation = AnnotationUtils
            .findAnnotation(beanClass, beanAnnotation.getAnnotationType());
        Object value = AnnotationUtils.getValue(annotation, beanAnnotation.getAttributeName());
        if (Objects.isNull(annotation)) {
          continue;
        }
        // 使用注解名作为默认标签名称
        if (StringUtils.isEmpty(beanAnnotation.getTagName())) {
          String lowerCamelCase = StringUtils
              .uncapitalize(annotation.getClass().getCanonicalName());
          beanAnnotation.setTagName(lowerCamelCase);
        }
        String t = beanAnnotation.getTagName() + ":" + Optional.ofNullable(value).orElse("");
        tags.add(t);
      }
    }
    identifyBuilder.tags(tags.toArray(new String[0]));

    List<FunctionHolder> annotationMethods = getAnnotationMethods(beanClass); // 获取函数

    // 构建标识实例持有者
    return new IdentifiedBeanHolder<>(identifyBuilder.build(), bean, annotationMethods);
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
