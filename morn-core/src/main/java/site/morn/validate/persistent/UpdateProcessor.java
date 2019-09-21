package site.morn.validate.persistent;

import site.morn.constant.PersistTypes;

/**
 * 删除处理
 *
 * @author timely-rain
 * @since 1.2.0, 2019/7/10
 */
@FunctionalInterface
public interface UpdateProcessor<T> extends PersistProcessor<T> {

  @Override
  default void handle(T source) {
    handle(PersistTypes.UPDATE, source);
  }
}
