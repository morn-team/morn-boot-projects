package site.morn.boot.data.jpa.crud;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import site.morn.boot.data.jpa.SpecificationFunction;
import site.morn.boot.data.jpa.support.SpecificationBuilder;
import site.morn.boot.data.jpa.support.SpecificationBuilder.SimpleFunction;
import site.morn.core.CriteriaMap;

/**
 * 基础数据访问实现
 *
 * @author timely-rain
 * @since 0.0.1, 2019/4/13 0013
 */
public class SpecificationRepositoryImpl<T, I extends Serializable> extends
    SimpleJpaRepository<T, I> implements SpecificationRepository<T, I> {

  public SpecificationRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
      EntityManager entityManager) {
    super(entityInformation, entityManager);
  }

  public SpecificationRepositoryImpl(Class<T> domainClass, EntityManager em) {
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
  public T findOne(SimpleFunction<T> simpleFunction) {
    Specification<T> specification = SpecificationBuilder.specification(simpleFunction);
    return super.findOne(specification).orElse(null);
  }

  @Override
  public T findOne(T model, SpecificationFunction specificationFunction) {
    return this.findOne(model, new CriteriaMap(), specificationFunction);
  }

  @Override
  public T findOne(T model, CriteriaMap attach, SpecificationFunction specificationFunction) {
    Specification<T> specification = SpecificationBuilder.withParameter(model, attach)
        .specification(specificationFunction);
    return super.findOne(specification).orElse(null);
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
