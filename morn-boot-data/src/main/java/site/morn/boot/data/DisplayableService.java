package site.morn.boot.data;

import java.io.Serializable;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * 显示/隐藏服务
 *
 * @author timely-rain
 * @since 2.1.1, 2019/8/14
 */
public interface DisplayableService<T, I extends Serializable> extends CrudService<T, I> {

  /**
   * 切换显示状态
   *
   * @param id        主键
   * @param isDisplay 显示状态
   * @param <S>       实体类型
   * @return 持久化对象
   */
  <S extends T> S toggleDisplay(I id, boolean isDisplay);

  /**
   * 显示
   *
   * @param id 主键
   * @param <S> 实体类型
   * @return 持久化对象
   */
  default <S extends T> S display(I id) {
    return toggleDisplay(id, true);
  }

  /**
   * 隐藏
   *
   * @param id 主键
   * @param <S> 实体类型
   * @return 持久化对象
   */
  default <S extends T> S hide(I id) {
    return toggleDisplay(id, false);
  }

  /**
   * 批量显示
   *
   * @param ids 主键集合
   * @return 是否成功
   */
  default boolean displayAll(List<I> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return false;
    }
    for (I id : ids) {
      display(id);
    }
    return true;
  }

  /**
   * 批量隐藏
   *
   * @param ids 主键集合
   * @return 是否成功
   */
  default boolean hideAll(List<I> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return false;
    }
    for (I id : ids) {
      hide(id);
    }
    return true;
  }
}
