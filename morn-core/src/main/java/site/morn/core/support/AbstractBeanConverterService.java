package site.morn.core.support;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import site.morn.bean.AnnotationFeature;
import site.morn.bean.support.AnnotationFeatureBuilder;
import site.morn.bean.support.BeanCaches;
import site.morn.core.BeanConverter;
import site.morn.core.BeanConverterService;
import site.morn.util.GenericUtils;

/**
 * 抽象实例转换服务
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/27
 */
@Slf4j
@RequiredArgsConstructor
public class AbstractBeanConverterService implements BeanConverterService {

  private final Class<? extends BeanConverter> converterClass;

  @Override
  public <S, T> T convert(S object, Class<T> targetClass) {
    Assert.notNull(object, "被转换对象不能为空");
    Assert.notNull(targetClass, "目标类型不能为空");
    Class<?> objectClass = object.getClass();
    if (targetClass.isAssignableFrom(objectClass)) {
      return GenericUtils.castFrom(object);
    }
    BeanConverter<S, T> converter = getConverter(objectClass, targetClass);
    Assert.notNull(converter,
        "尚未注册合适的转换器：convert " + objectClass.getSimpleName() + " to " + targetClass.getSimpleName());
    return converter.convert(object);
  }

  @Override
  public <S, T> T revert(S object, Class<T> targetClass) {
    Assert.notNull(object, "被还原对象不能为空");
    Assert.notNull(targetClass, "目标对象类型不能为空");
    Class<?> objectClass = object.getClass();
    if (targetClass.isAssignableFrom(objectClass)) {
      return GenericUtils.castFrom(object);
    }
    BeanConverter<T, S> resolver = getResolver(objectClass, targetClass);
    Assert.notNull(resolver,
        "尚未注册合适的解析器：revert " + objectClass.getSimpleName() + " to " + targetClass.getSimpleName());
    return resolver.revert(object);
  }

  @Override
  public <S, T> T transform(S object, Class<T> targetClass) {
    Assert.notNull(object, "被转变对象不能为空");
    if (Objects.isNull(targetClass)) {
      return null;
    }
    Class<?> sourceClass = object.getClass();
    if (targetClass.isAssignableFrom(sourceClass)) {
      return null;
    }
    return transformTo(object, sourceClass, targetClass);
  }

  @Override
  public <S> Object deduce(S object, Class<?> targetClass) {
    Assert.notNull(object, "被推断对象不能为空");
    if (Objects.isNull(targetClass)) {
      return object;
    }
    Class<?> sourceClass = object.getClass();
    if (targetClass.isAssignableFrom(sourceClass)) {
      return object;
    }
    Object target = transformTo(object, sourceClass, targetClass);
    if (Objects.isNull(target)) {
      return object;
    }
    return target;
  }

  /**
   * 获取转换器
   */
  @SuppressWarnings("unchecked")
  protected <S, T> BeanConverter<S, T> getConverter(Class<?> sourceClass, Class<?> targetClass) {
    AnnotationFeature annotationFeature = AnnotationFeatureBuilder.withSource(sourceClass)
        .target(targetClass).build();
    return BeanCaches.bean(converterClass, annotationFeature);
  }

  /**
   * 获取解析器
   */
  @SuppressWarnings("unchecked")
  protected <T, S> BeanConverter<T, S> getResolver(Class<?> sourceClass, Class<?> targetClass) {
    AnnotationFeature annotationFeature = AnnotationFeatureBuilder.withSource(targetClass)
        .target(sourceClass).build();
    return BeanCaches.bean(converterClass, annotationFeature);
  }

  /**
   * 转换对象
   */
  private <S, T> T converterTo(S object, Class<?> sourceClass, Class<T> targetClass) {
    BeanConverter<S, T> converter = getConverter(sourceClass, targetClass);
    if (Objects.isNull(converter)) {
      return null;
    }
    return converter.convert(object);
  }

  /**
   * 还原对象
   */
  private <S, T> T revertTo(S object, Class<?> sourceClass, Class<T> targetClass) {
    BeanConverter<T, S> resolver = getResolver(sourceClass, targetClass);
    if (Objects.isNull(resolver)) {
      return null;
    }
    return resolver.revert(object);
  }

  /**
   * 转变对象
   */
  private <S, T> T transformTo(S object, Class<?> sourceClass, Class<T> targetClass) {
    // convert
    T conversion = converterTo(object, sourceClass, targetClass);
    if (conversion != null) {
      return conversion;
    }
    // revert
    T reversion = revertTo(object, sourceClass, targetClass);
    if (reversion != null) {
      return reversion;
    }
    log.warn("尚未注册合适的转换器/解析器：from {} to {}", sourceClass.getSimpleName(),
        targetClass.getSimpleName());
    return null;
  }
}
