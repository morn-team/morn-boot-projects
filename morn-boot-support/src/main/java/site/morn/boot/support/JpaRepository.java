package site.morn.boot.support;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 基础数据访问
 *
 * @author timely-rain
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
@NoRepositoryBean
public interface JpaRepository<T, I extends Serializable> extends PagingAndSortingRepository<T, I>,
    JpaSpecificationExecutor<T> {

  /**
   * 查询唯一数据, 精确匹配
   *
   * @param model 查询参数
   * @return 唯一实例
   */
  T findOne(T model);

  /**
   * 查询所有数据, 精确匹配
   *
   * @param model 查询参数
   * @return 实例集合
   */
  List<T> findAll(T model);
}
