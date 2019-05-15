package site.morn.boot.support;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import site.morn.boot.jpa.SpecificationBuilder;
import site.morn.core.CriteriaMap;

/**
 * 基础数据访问实现
 *
 * @author timely-rain
 * @since 0.0.1, 2019/4/13 0013
 */
public class JpaRepositoryImpl<T, I extends Serializable> extends
    SimpleJpaRepository<T, I> implements JpaRepository<T, I> {

  public JpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
      EntityManager entityManager) {
    super(entityInformation, entityManager);
  }

  public JpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
    super(domainClass, em);
  }

  @Override
  public T findOne(T model) {
    Specification<T> specification = matchSpecification(model);
    PageRequest pageRequest = PageRequest.of(0, 1);
    Page<T> page = findAll(specification, pageRequest);
    List<T> list = page.getContent();
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public List<T> findAll(T model) {
    Specification<T> specification = matchSpecification(model);
    return findAll(specification);
  }

  /**
   * 构建查询条件
   *
   * @param model 查询参数
   * @return 查询条件
   */
  protected Specification<T> matchSpecification(T model) {
    return SpecificationBuilder.withParameter(model, new CriteriaMap())
        .specification((reference, restrain, predicate) -> {
          Predicate[] equalAll = predicate.equalAll(); // 精确匹配所有属性
          restrain.appendAnd(equalAll);
        });
  }
}
