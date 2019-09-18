package site.morn.boot.data;

import java.io.Serializable;
import site.morn.boot.data.jpa.JpaRepository;
import site.morn.data.Displayable;
import site.morn.util.TypeUtils;

/**
 * 显示/隐藏服务实现
 *
 * @author timely-rain
 * @since 1.0.0, 2019/8/14
 */
public class DisplayableServiceSupport<T extends Displayable, I extends Serializable, R extends JpaRepository<T, I>> extends
    CrudServiceSupport<T, I, R> implements CrudService<T, I>, DisplayableService<T, I> {

  @Override
  public <S extends T> S toggleDisplay(I id, boolean isDisplay) {
    T result = repository().findById(id).map(model -> toggleDisplay(model, isDisplay)).orElse(null);
    return TypeUtils.cast(result);
  }

  /**
   * 切换显示状态
   */
  protected T toggleDisplay(T model, boolean isDisplay) {
    model.setDisplay(isDisplay);
    return update(model);
  }
}
