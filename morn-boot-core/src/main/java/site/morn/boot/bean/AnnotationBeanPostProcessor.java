package site.morn.boot.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.morn.bean.AnnotationFeature;
import site.morn.bean.AnnotationField;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.BeanCache;
import site.morn.bean.BeanHolder;
import site.morn.bean.FunctionHolder;
import site.morn.bean.annotation.Objective;
import site.morn.bean.annotation.Tag;
import site.morn.bean.support.AnnotationFeatureBuilder;
import site.morn.boot.util.BeanCacheUtils;
import site.morn.util.AnnotationFeatureUtils;
import site.morn.util.GenericUtils;

/**
 * 注解实例后置处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/28
 */
@Slf4j
public class AnnotationBeanPostProcessor implements BeanPostProcessor {

  /**
   * 实例注解注册表
   */
  private final AnnotationFieldRegistry beanAnnotationRegistry;

  /**
   * 函数注解注册表
   */
  private final AnnotationFieldRegistry functionAnnotationRegistry;

  /**
   * 标识实例缓存
   */
  private final BeanCache beanCache;

  public AnnotationBeanPostProcessor(AnnotationFieldRegistry beanAnnotationRegistry,
      AnnotationFieldRegistry functionAnnotationRegistry, BeanCache beanCache) {
    this.beanAnnotationRegistry = beanAnnotationRegistry;
    this.functionAnnotationRegistry = functionAnnotationRegistry;
    this.beanCache = beanCache;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    // 获取所有实例标识注解
    BeanHolder<Object> beanHolder = generateBeanHolder(bean, beanAnnotationRegistry);
    if (Objects.isNull(beanHolder)) {
      return bean;
    }
    log.info("注册标识实例：{}", beanName);
    // 缓存实例
    beanCache.put(beanName, beanHolder);
    return bean;
  }

  /**
   * 生成实例持有者
   *
   * @param bean 实例
   * @param <T> 实例类型
   * @return 实例持有者
   */
  private <T> BeanHolder<T> generateBeanHolder(T bean, AnnotationFieldRegistry registry) {
    // 获取根实例注解
    Class<?> beanClass = bean.getClass();
    Objective objective = AnnotationUtils.findAnnotation(beanClass, Objective.class);
    // 获取实例中声明的注解
    List<AnnotationField> annotationFields = findAnnotationFields(beanClass, registry);

    // 未检测到有效注解
    if (Objects.isNull(objective) && CollectionUtils.isEmpty(annotationFields)) {
      return null;
    }

    // 生成类描述
    AnnotationFeature beanFeature = generateAnnotationFeature(beanClass, annotationFields);
    // 生成函数持有者
    List<FunctionHolder> functionHolders = generateFunctionHolders(bean, beanClass,
        functionAnnotationRegistry); // 获取函数

    // 构建实例持有者
    return new BeanHolder<>(beanFeature, bean, functionHolders);
  }

  /**
   * 生成函数持有者
   *
   * @param bean 实例
   * @param beanClass 实例类
   * @param registry 函数注册表
   * @return 函数持有者
   */
  private <T> List<FunctionHolder> generateFunctionHolders(T bean, Class<?> beanClass,
      AnnotationFieldRegistry registry) {
    List<FunctionHolder> functionHolders = new ArrayList<>();

    for (Method method : beanClass.getMethods()) {
      List<AnnotationField> annotationFields = findAnnotationFields(method, registry);
      if (CollectionUtils.isEmpty(annotationFields)) {
        continue;
      }
      AnnotationFeature feature = generateAnnotationFeature(method, annotationFields);
      String functionName =
          StringUtils.isEmpty(feature.getName()) ? method.getName() : feature.getName();
      Assert.isTrue(!BeanCacheUtils.contains(functionHolders, functionName), String
          .format("函数重复注入：%s.%s#%s", beanClass.getSimpleName(), method.getName(), functionName));
      FunctionHolder functionHolder = new FunctionHolder(bean, feature, method,
          method.getParameterTypes());
      functionHolders.add(functionHolder);
      log.info("注册缓存函数：{}.{}#{}", beanClass.getSimpleName(), method.getName(),
          functionHolder.getMethodPath());
    }
    return functionHolders;
  }

  /**
   * 检索注解
   *
   * @param classOrMethod 类或方法
   * @param annotationClass 注解类
   * @return 注解
   */
  private Annotation findAnnotation(Object classOrMethod,
      Class<? extends Annotation> annotationClass) {
    if (classOrMethod instanceof Class) {
      return AnnotationUtils.findAnnotation((Class<?>) classOrMethod, annotationClass);
    }
    if (classOrMethod instanceof Method) {
      return AnnotationUtils.findAnnotation((Method) classOrMethod, annotationClass);
    }
    Assert.notNull(classOrMethod,
        "BeanCache|Annotation object not support: " + classOrMethod.getClass());
    return null;
  }

  /**
   * 检索类的自定义注解属性
   *
   * @param beanClass 实例
   * @param registry 实例注解属性注册表
   * @return 类的自定义注解属性
   */
  private List<AnnotationField> findAnnotationFields(Class<?> beanClass,
      AnnotationFieldRegistry registry) {
    return registry.getAnnotations().stream().filter(
        annotationField -> Objects.nonNull(
            AnnotationUtils.findAnnotation(beanClass, annotationField.getAnnotationClass())
        )
    ).collect(Collectors.toList());
  }

  /**
   * 检索方法的自定义注解属性
   *
   * @param method 方法
   * @param registry 函数注解属性注册表
   * @return 方法的自定义注解属性
   */
  private List<AnnotationField> findAnnotationFields(Method method,
      AnnotationFieldRegistry registry) {
    return registry.getAnnotations().stream().filter(
        annotationField -> Objects.nonNull(
            AnnotationUtils.findAnnotation(method, annotationField.getAnnotationClass())
        )
    ).collect(Collectors.toList());
  }

  /**
   * 生成注解特征
   *
   * @param classOrMethod 类或方法
   * @param annotationFields 注解属性
   * @return 注解特征
   */
  private AnnotationFeature generateAnnotationFeature(Object classOrMethod,
      List<AnnotationField> annotationFields) {
    // 构建实例标识信息
    AnnotationFeatureBuilder featureBuilder = AnnotationFeatureBuilder.empty();
    List<String> tags = new ArrayList<>(); // 标签
    for (AnnotationField annotationField : annotationFields) {
      Class<? extends Annotation> annotationClass = annotationField.getAnnotationClass();
      Annotation annotation = findAnnotation(classOrMethod, annotationClass);
      Assert.notNull(annotation, "BeanCache|Annotation no found: " + annotationClass);
      String valueName = annotationField.getValueName();
      Object value = AnnotationUtils.getValue(annotation, valueName);
      Assert.notNull(value,
          "BeanCache|Annotation value is null:" + annotationClass + "." + valueName);
      switch (annotationField.getAnnotationType()) {
        case NAME:
          writeNameValue(featureBuilder, classOrMethod, value);
          break;
        case TAG:
          writeTagValue(tags, annotationField, value);
          break;
        case SOURCE:
          writeSourceValue(featureBuilder, value);
          break;
        case TARGET:
          writeTargetValue(featureBuilder, value);
          break;
        default:
          log.warn(
              "BeanCache|Annotation type is not support: " + annotationField.getAnnotationType());
          break;
      }
    }
    featureBuilder.tags(tags.toArray(new String[0]));
    return featureBuilder.build();
  }

  /**
   * 写入命名值
   *
   * @param featureBuilder 构建器
   * @param classOrMethod 类或方法
   * @param value 注解值
   */
  private void writeNameValue(AnnotationFeatureBuilder featureBuilder, Object classOrMethod,
      Object value) {
    if (StringUtils.isEmpty(value)) {
      featureBuilder.name(getAnnotationNameValue(classOrMethod));
    } else {
      Assert.isInstanceOf(String.class, value);
      featureBuilder.name(String.valueOf(value));
    }
  }

  /**
   * 写入标签值
   *
   * @param tags 全部标签
   * @param annotationField 注解属性
   * @param value 注解值
   */
  private void writeTagValue(List<String> tags, AnnotationField annotationField, Object value) {
    if (Objects.equals(Tag.class, annotationField.getAnnotationClass())) {
      Assert.isInstanceOf(String[].class, value);
      Collections.addAll(tags, GenericUtils.castFrom(value));
    } else {
      String annotationName = getAnnotationName(annotationField);
      String tag = AnnotationFeatureUtils.getTag(annotationName, value);
      tags.add(tag);
    }
  }

  /**
   * 写入源
   *
   * @param featureBuilder 构建器
   * @param value 注解值
   */
  private void writeSourceValue(AnnotationFeatureBuilder featureBuilder, Object value) {
    Assert.isInstanceOf(Class.class, value);
    featureBuilder.source(GenericUtils.castFrom(value));
  }

  /**
   * 写入目标
   *
   * @param featureBuilder 构建器
   * @param value 注解值
   */
  private void writeTargetValue(AnnotationFeatureBuilder featureBuilder, Object value) {
    Assert.isInstanceOf(Class.class, value);
    featureBuilder.target(GenericUtils.castFrom(value));
  }

  /**
   * 获取注解名称
   *
   * @param annotationField 注解属性
   * @return 注解名称
   */
  private String getAnnotationName(AnnotationField annotationField) {
    String annotationName = annotationField.getAnnotationName();
    if (StringUtils.isEmpty(annotationName)) {
      return StringUtils.uncapitalize(annotationField.getAnnotationClass().getSimpleName());
    }
    return annotationName;
  }

  /**
   * 获取注解名称的值
   *
   * @param classOrMethod 类或方法
   * @return 注解名称的值
   */
  private String getAnnotationNameValue(Object classOrMethod) {
    if (classOrMethod instanceof Class) {
      return StringUtils.uncapitalize(((Class<?>) classOrMethod).getSimpleName());
    }
    if (classOrMethod instanceof Method) {
      return ((Method) classOrMethod).getName();
    }
    Assert.notNull(classOrMethod,
        "BeanCache|Annotation object not support: " + classOrMethod.getClass());
    return null;
  }
}
