package site.morn.boot.data;

import java.io.Serializable;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * 锁定/解锁服务
 *
 * @author timely-rain
 * @since 2.1.1, 2019/8/14
 */
public interface LockableService<T, I extends Serializable> {

  /**
   * 切换显示状态
   *
   * @param id 主键
   * @param isLock 显示状态
   * @param <S> 实体类型
   * @return 持久化对象
   */
  <S extends T> S toggleLock(I id, boolean isLock);

  /**
   * 锁定
   *
   * @param id 主键
   * @param <S> 实体类型
   * @return 持久化对象
   */
  default <S extends T> S lock(I id) {
    return toggleLock(id, true);
  }

  /**
   * 解锁
   *
   * @param id 主键
   * @param <S> 实体类型
   * @return 持久化对象
   */
  default <S extends T> S unlock(I id) {
    return toggleLock(id, false);
  }

  /**
   * 批量锁定
   *
   * @param ids 主键集合
   * @return 是否成功
   */
  default boolean lockAll(List<I> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return false;
    }
    for (I id : ids) {
      lock(id);
    }
    return true;
  }

  /**
   * 批量解锁
   *
   * @param ids 主键集合
   * @return 是否成功
   */
  default boolean unlockAll(List<I> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return false;
    }
    for (I id : ids) {
      unlock(id);
    }
    return true;
  }
}
