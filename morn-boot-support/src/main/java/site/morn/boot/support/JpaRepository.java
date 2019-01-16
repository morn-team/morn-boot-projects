package site.morn.boot.support;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 基础数据访问
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
@NoRepositoryBean
public interface JpaRepository<T, I extends Serializable> extends PagingAndSortingRepository<T, I>,
    JpaSpecificationExecutor<T> {

}
