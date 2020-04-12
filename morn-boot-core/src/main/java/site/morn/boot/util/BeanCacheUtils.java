package site.morn.boot.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.morn.bean.AnnotationIdentifyCase;
import site.morn.bean.AnnotationIdentifyCase.AnnotationIdentifyCaseBuilder;
import site.morn.bean.BeanAnnotation;
import site.morn.bean.BeanAnnotationRegistry;
import site.morn.bean.FunctionHolder;
import site.morn.bean.IdentifiedBeanHolder;
import site.morn.bean.annotation.Function;
import site.morn.bean.annotation.Name;
import site.morn.bean.annotation.Objective;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;
import site.morn.util.AnnotationIdentifyUtils;

/**
 * 实例缓存工具类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/24
 */
@Slf4j
@UtilityClass
public class BeanCacheUtils {


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
  private static <T> List<FunctionHolder> getAnnotationMethods(T bean, Class<?> beanClass) {
    List<FunctionHolder> functionHolders = new ArrayList<>();

    for (Method method : beanClass.getMethods()) {
      Function function = AnnotationUtils.findAnnotation(method, Function.class);
      if (Objects.isNull(function)) {
        continue;
      }
      String functionName =
          StringUtils.isEmpty(function.value()) ? method.getName() : function.value();
      Assert.isTrue(!contains(functionHolders, functionName), String
          .format("函数重复注入：%s.%s#%s", beanClass.getSimpleName(), method.getName(), functionName));
      AnnotationIdentifyCaseBuilder builder = AnnotationIdentifyCase.builder().name(functionName);
      FunctionHolder functionHolder = new FunctionHolder(bean, builder.build(), method,
          method.getParameterTypes());
      functionHolders.add(functionHolder);
      log.warn("注册缓存函数：{}.{}#{}", beanClass.getSimpleName(), method.getName(), functionName);
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
  public static <T> IdentifiedBeanHolder<T> generateBeanHolder(T bean,
      BeanAnnotationRegistry registry) {
    Class<?> beanClass = bean.getClass();

    // 获取根实例注解
    Objective objective = AnnotationUtils.findAnnotation(beanClass, Objective.class);
    // 获取自定义注解
    List<BeanAnnotation> beanAnnotations = registry.getAnnotations();
    List<? extends Annotation> annotations = beanAnnotations.stream().map(
        beanAnnotation -> AnnotationUtils
            .findAnnotation(beanClass, beanAnnotation.getAnnotationType())).filter(Objects::nonNull)
        .collect(Collectors.toList());

    // 未检测到有效注解
    if (Objects.isNull(objective) && CollectionUtils.isEmpty(annotations)) {
      return null;
    }

    // 获取标准实例注解
    Name name = AnnotationUtils.findAnnotation(beanClass, Name.class);
    Tag tag = AnnotationUtils.findAnnotation(beanClass, Tag.class);
    Target target = AnnotationUtils.findAnnotation(beanClass, Target.class);

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
        if (Objects.isNull(annotation)) {
          continue;
        }
        Object value = AnnotationUtils.getValue(annotation, beanAnnotation.getAttributeName());
        String t = AnnotationIdentifyUtils.getTag(beanAnnotation.getTagName(), value);
        tags.add(t);
      }
    }
    identifyBuilder.tags(tags.toArray(new String[0])); // 自定义注解作为Tag

    List<FunctionHolder> annotationMethods = getAnnotationMethods(bean, beanClass); // 获取函数

    // 构建标识实例持有者
    return new IdentifiedBeanHolder<>(identifyBuilder.build(), bean, annotationMethods);
  }
}
