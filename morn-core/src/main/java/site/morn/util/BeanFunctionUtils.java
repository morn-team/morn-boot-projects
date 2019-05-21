package site.morn.util;

import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;
import site.morn.bean.BeanCaches;
import site.morn.core.BeanAdapter;
import site.morn.core.BeanConverter;
import site.morn.core.BeanProcessor;
import site.morn.core.BeanProducer;

/**
 * 实例函数工具
 *
 * @author timely-rain
 * @since 1.0.2, 2019/4/30
 */
@UtilityClass
public class BeanFunctionUtils {

  /**
   * 实例适配
   *
   * @param functionClass 转换反射类
   * @param source 源对象
   * @param tags 检索标签
   * @param <F> 适配类型
   * @param <S> 源类型
   * @return 目标实例
   */
  public <F extends BeanAdapter<S>, S> S adaption(Class<F> functionClass, S source,
      String... tags) {
    F adapter = BeanCaches.tagBean(functionClass, tags);
    Assert.notNull(adapter, "尚未注册可用适配器：" + functionClass.getSimpleName());
    return adapter.adaption(source);
  }

  /**
   * 实例适配
   *
   * @param functionClass 转换反射类
   * @param source 源对象
   * @param target 检索目标
   * @param <F> 适配类型
   * @param <S> 源类型
   * @return 目标实例
   */
  public <F extends BeanAdapter<S>, S> S adaption(Class<F> functionClass, S source,
      Class<S> target) {
    F adapter = BeanCaches.targetBean(functionClass, target);
    Assert.notNull(adapter, "尚未注册可用适配器：" + functionClass.getSimpleName());
    return adapter.adaption(source);
  }

  /**
   * 实例适配
   *
   * @param functionClass 转换反射类
   * @param source 源对象
   * @param tags 检索标签
   * @param <F> 适配类型
   * @param <S> 源类型
   * @return 目标实例
   */
  public <F extends BeanAdapter<S>, S> S adaptions(Class<F> functionClass, S source,
      String... tags) {
    List<F> adapters = BeanCaches.tagBeans(functionClass, tags);
    S target = null;
    for (F adapter : adapters) {
      target = adapter.adaption(source);
    }
    if (Objects.isNull(target)) {
      return source;
    }
    return target;
  }

  /**
   * 实例转换
   *
   * @param functionClass 转换反射类
   * @param source 源对象
   * @param tags 检索标签
   * @param <F> 转换类型
   * @param <S> 源类型
   * @param <T> 目标类型
   * @return 目标实例
   */
  public <F extends BeanConverter<S, T>, S, T> T convert(Class<F> functionClass, S source,
      String... tags) {
    F converter = BeanCaches.tagBean(functionClass, tags);
    Assert.notNull(converter, "尚未注册可用转换器：" + functionClass.getSimpleName());
    return converter.convert(source);
  }

  /**
   * 实例转换
   *
   * @param functionClass 转换反射类
   * @param source 源对象
   * @param target 检索目标
   * @param <F> 转换类型
   * @param <S> 源类型
   * @param <T> 目标类型
   * @return 目标实例
   */
  public <F extends BeanConverter, S, T> T convert(Class<F> functionClass, S source,
      Class<T> target) {
    @SuppressWarnings("unchecked")
    BeanConverter<S, T> converter = BeanCaches.targetBean(functionClass, target);
    Assert.notNull(converter, "尚未注册可用转换器：" + functionClass.getSimpleName());
    return converter.convert(source);
  }

  /**
   * 实例转换
   *
   * @param functionClass 转换反射类
   * @param source 源对象
   * @param tags 检索标签
   * @param <F> 转换类型
   * @param <S> 源类型
   * @param <T> 目标类型
   * @return 目标实例
   */
  public <F extends BeanConverter<S, T>, S, T> T converts(Class<F> functionClass, S source,
      String... tags) {
    List<F> converters = BeanCaches.tagBeans(functionClass, tags);
    return converts(converters, source);
  }

  /**
   * 实例转换
   *
   * @param converters 实例转换器
   * @param source 源对象
   * @param <F> 转换类型
   * @param <S> 源类型
   * @param <T> 目标类型
   * @return 目标实例
   */
  private <F extends BeanConverter<S, T>, S, T> T converts(List<F> converters, S source) {
    T target = null;
    for (F converter : converters) {
      target = converter.convert(source);
    }
    return target;
  }

  /**
   * 实例处理
   *
   * @param functionClass 转换反射类
   * @param source 源对象
   * @param target 检索目标
   * @param <F> 适配类型
   * @param <S> 源类型
   */
  public <F extends BeanProcessor<S>, S> void process(Class<F> functionClass, S source,
      Class<?> target) {
    BeanProcessor<S> processor = BeanCaches.targetBean(functionClass, target);
    Assert.notNull(processor, "尚未注册可用处理器：" + functionClass.getSimpleName());
    processor.handle(source);
  }

  /**
   * 实例处理
   *
   * @param functionClass 转换反射类
   * @param source 源对象
   * @param tags 检索标签
   * @param <F> 适配类型
   * @param <S> 源类型
   * @return 是否完成
   */
  public <F extends BeanProcessor<S>, S> boolean processes(Class<F> functionClass, S source,
      String... tags) {
    List<F> processors = BeanCaches.tagBeans(functionClass, tags);
    if (processors.isEmpty()) {
      return false;
    }
    for (F processor : processors) {
      processor.handle(source);
    }
    return true;
  }

  /**
   * 实例生产
   *
   * @param functionClass 函数类
   * @param tags 检索标签
   * @param <F> 函数类型
   * @param <T> 目标类型
   * @return 实例
   */
  public <F extends BeanProducer<T>, T> T product(Class<F> functionClass, String... tags) {
    F producer = BeanCaches.tagBean(functionClass, tags);
    Assert.notNull(producer, "尚未注册可用生产者：" + functionClass.getSimpleName());
    return producer.product();
  }

}
