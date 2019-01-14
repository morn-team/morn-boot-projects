package site.morn.boot.support;

import java.io.Serializable;

/**
 * site.morn.boot.support
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/14
 */
public interface CrudServiceDriven<T, I extends Serializable> {

  CrudService<T, I> getService();

  void setService(CrudService<T, I> service);
}
