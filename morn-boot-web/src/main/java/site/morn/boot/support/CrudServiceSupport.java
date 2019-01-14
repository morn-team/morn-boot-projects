package site.morn.boot.support;

import java.io.Serializable;
import org.springframework.data.domain.Page;
import site.morn.boot.rest.RestPage;
import site.morn.rest.RestModel;

/**
 * site.morn.boot.support
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
public class CrudServiceSupport<T, I extends Serializable> implements CrudService<T, I> {

  @Override
  public <S extends T> S add(RestModel<S> restModel) {
    return null;
  }

  @Override
  public Page<T> search(RestPage<T> restPage) {
    return null;
  }

  @Override
  public <S extends T> S update(RestModel<S> restModel) {
    return null;
  }

  @Override
  public <S extends T> S patch(RestModel<S> restModel) {
    return null;
  }

  @Override
  public void delete(I id) {

  }

  @Override
  public <S extends T> void delete(RestModel<S> restModel) {

  }

  @Override
  public void delete(Iterable<? extends I> ids) {

  }
}
