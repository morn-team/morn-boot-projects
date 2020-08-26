package site.morn.boot.data.jpa.crud.impl;

import java.io.Serializable;
import site.morn.boot.data.CrudService;
import site.morn.boot.data.DisplayableService;
import site.morn.boot.data.jpa.crud.SpecificationRepository;
import site.morn.data.Displayable;
import site.morn.util.GenericUtils;

/**
 * 显示/隐藏服务实现
 *
 * @author timely-rain
 * @since 1.0.0, 2019/8/14
 */
public class SimpleDisplayableService<T extends Displayable, I extends Serializable, R extends SpecificationRepository<T, I>> extends
    SimpleCrudService<T, I, R> implements CrudService<T, I>, DisplayableService<T, I> {

  @Override
  public <S extends T> S toggleDisplay(I id, boolean isDisplay) {
    T result = repository().findById(id).map(model -> toggleDisplay(model, isDisplay)).orElse(null);
    return GenericUtils.castFrom(result);
  }

  /**
   * 切换显示状态
   */
  protected T toggleDisplay(T model, boolean isDisplay) {
    model.setDisplay(isDisplay);
    return update(model);
  }
}
