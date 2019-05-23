package site.morn.boot.support;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import site.morn.boot.jpa.SpecificationBuilder;
import site.morn.boot.jpa.SpecificationFunction;
import site.morn.boot.rest.RestPage;
import site.morn.core.CriteriaMap;
import site.morn.rest.RestModel;
import site.morn.util.TypeUtils;
import site.morn.validate.persistent.PersistValidateUtils;

/**
 * 基础服务实现
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
@Slf4j
@Getter
public abstract class CrudServiceSupport<T, I extends Serializable, R extends JpaRepository<T, I>>
    implements CrudService<T, I> {

  @Autowired
  private JpaRepository<T, I> repository;

  /**
   * 数据访问对象
   */
  protected R repository() {
    return TypeUtils.as(repository);
  }

  @Override
  public T get(I id) {
    return repository().findById(id).orElse(null);
  }

  @Override
  public <S extends T> S add(S model) {
    PersistValidateUtils.validateAdd(model);
    return repository.save(model);
  }

  @Override
  public <S extends T> S add(RestModel<S> restModel) {
    S model = restModel.getModel();
    return this.add(model);
  }

  @Override
  public Page<T> search(RestPage<T> restPage) {
    log.info("分页搜索");
    PageRequest pageRequest = restPage.generatePageRequest();// 分页请求
    CriteriaMap attach = restPage.getAttach(); // 附加数据
    T model = restPage.getModel(); // 数据模型
    Specification<T> specification = searchSpecification(model, attach);// 查询条件
    return repository.findAll(specification, pageRequest); // 分页查询
  }

  @Override
  public List<T> searchAll(RestModel<T> restModel) {
    log.info("全部搜索");
    CriteriaMap attach = restModel.getAttach(); // 附加数据
    T model = restModel.getModel(); // 数据模型
    Specification<T> specification = searchSpecification(model, attach);// 查询条件
    return repository.findAll(specification); // 分页查询
  }

  @Override
  public <S extends T> S update(S model) {
    PersistValidateUtils.validateUpdate(model);
    return repository.save(model);
  }

  @Override
  public <S extends T> S update(RestModel<S> restModel) {
    S model = restModel.getModel();
    return this.update(model);
  }

  @Override
  public <S extends T> S patch(S model) {
    PersistValidateUtils.validateUpdate(model);
    return repository.save(model);
  }

  @Override
  public <S extends T> S patch(RestModel<S> restModel) {
    S model = restModel.getModel();
    return this.patch(model);
  }

  @Override
  public void delete(I id) {
    Optional<T> optional = repository().findById(id);
    if (optional.isPresent()) {
      PersistValidateUtils.validateDelete(optional.get()); // 数据删除校验
      repository.deleteById(id);
    } else {
      log.warn("数据不存在：[id={}]", id);
    }
  }

  @Override
  public <S extends T> void delete(RestModel<S> restModel) {
    S model = restModel.getModel();
    PersistValidateUtils.validateDelete(model); // 数据删除校验
    repository.delete(model);
  }

  @Override
  public void delete(Iterable<? extends I> ids) {
  }

  /**
   * 构建搜索条件
   *
   * @param model 数据模型
   * @param attach 附加数据
   * @return 搜索条件
   */
  protected Specification<T> searchSpecification(T model, CriteriaMap attach) {
    SpecificationFunction specificationFunction = searchSpecificationFunction(model, attach);
    return SpecificationBuilder.withParameter(model, attach).specification(specificationFunction);
  }

  /**
   * 构建搜索条件
   *
   * @param model 数据模型
   * @param attach 附加数据
   * @return 搜索条件
   */
  protected SpecificationFunction searchSpecificationFunction(T model, CriteriaMap attach) {
    return (reference, restrain, predicate) -> {
      Predicate[] equalAll = predicate.equalAll(); // 默认精确匹配所有属性
      restrain.appendAnd(equalAll);
    };
  }
}
