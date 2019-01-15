package site.morn.boot.support;

import java.io.Serializable;

/**
 * site.morn.boot.support
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/14
 */
public interface JpaRepositoryDriven<T, I extends Serializable> {

  void setRepository(JpaRepository<T, I> repository);
}
