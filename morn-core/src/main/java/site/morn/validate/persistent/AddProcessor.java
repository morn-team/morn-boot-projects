package site.morn.validate.persistent;

import site.morn.constant.PersistTypes;

/**
 * 新增处理
 *
 * @author timely-rain
 * @since 2.1.0, 2019/7/10
 */
@FunctionalInterface
public interface AddProcessor<T> extends PersistProcessor<T> {

  @Override
  default void handle(T source) {
    handle(PersistTypes.ADD, source);
  }
}
