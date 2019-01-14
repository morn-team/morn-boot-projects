package site.morn.boot.support;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * site.morn.boot.support
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
public interface JpaRepository<T, I extends Serializable> extends PagingAndSortingRepository<T, I>,
    JpaSpecificationExecutor<T> {

}
