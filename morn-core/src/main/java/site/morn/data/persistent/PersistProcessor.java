package site.morn.data.persistent;

import site.morn.core.BeanProcessor;
import site.morn.exception.ApplicationException;

/**
 * 持久化处理者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/10
 */
@FunctionalInterface
public interface PersistProcessor<T> extends BeanProcessor<T> {

  /**
   * 持久化操作处理
   * <p>处理失败时，通常抛出{@link ApplicationException}以回滚事务
   * <p>若处理函数与持久化业务无关，则应阻止异常抛出，以避免影响持久化事务</p>
   *
   * @param data 持久化操作数据
   */
  @Override
  void handle(T data);
}
