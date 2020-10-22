package site.morn.core;

import site.morn.bean.BeanCache;

/**
 * 实例转换服务
 * <p>用于调度{@link BeanConverter}完成源对象到目标对象的转换</p>
 * <p>{@link BeanConverter}的实现类通常存储于{@link BeanCache}中</p>
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/27
 */
public interface BeanConverterService {

  /**
   * 转换对象
   *
   * @param object      对象
   * @param targetClass 目标类
   * @param <S>         对象类型
   * @param <T>         目标类型
   * @return 目标对象
   */
  <S, T> T convert(S object, Class<T> targetClass);

  /**
   * 还原对象
   *
   * @param object      对象
   * @param targetClass 目标类
   * @param <S>         对象类型
   * @param <T>         目标类型
   * @return 目标对象
   */
  <S, T> T revert(S object, Class<T> targetClass);

  /**
   * 变换对象
   *
   * @param object      对象
   * @param targetClass 目标类
   * @param <S>         对象类型
   * @param <T>         目标类型
   * @return <ul><li>Success:目标对象</li><li>Failure:null</li></ul>
   */
  <S, T> T transform(S object, Class<T> targetClass);

  /**
   * 推断对象
   * <p>对象不一定会转换为<code>targetClass</code>类型对象，有时也会转为其它类型对象</p>
   *
   * @param object      对象
   * @param targetClass 目标类
   * @param <S>         对象类型
   * @return <ul><li>Success:目标对象</li><li>Failure:<code>object</code></li></ul>
   */
  <S> Object deduce(S object, Class<?> targetClass);
}
