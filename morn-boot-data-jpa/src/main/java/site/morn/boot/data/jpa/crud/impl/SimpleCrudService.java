package site.morn.boot.data.jpa.crud.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import site.morn.boot.data.CrudService;
import site.morn.boot.data.jpa.SpecificationFunction;
import site.morn.boot.data.jpa.crud.SpecificationRepository;
import site.morn.boot.data.jpa.support.SpecificationBuilder;
import site.morn.boot.data.rest.RestPageUtils;
import site.morn.boot.data.util.EntityUtils;
import site.morn.core.CriteriaMap;
import site.morn.rest.RestModel;
import site.morn.rest.RestPage;
import site.morn.util.GenericUtils;
import site.morn.util.PersistFunctionUtils;

/**
 * 基础服务实现
 *
 * @author timely-rain
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
@Slf4j
@Getter
public class SimpleCrudService<T, I extends Serializable, R extends SpecificationRepository<T, I>>
    implements CrudService<T, I> {

  @Autowired
  private SpecificationRepository<T, I> repository;

  /**
   * 数据访问对象
   */
  protected R repository() {
    return GenericUtils.castFrom(repository);
  }

  @Override
  public T get(I id) {
    return repository().findById(id).orElse(null);
  }

  @Override
  public <S extends T> S add(S model) {
    PersistFunctionUtils.validateAdd(model);
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
    PageRequest pageRequest = RestPageUtils.generatePageRequest(restPage);// 分页请求
    CriteriaMap attach = restPage.getAttach(); // 附加数据
    T model = restPage.getModel(); // 数据模型
    Specification<T> specification = searchSpecification(model, attach);// 查询条件
    return repository.findAll(specification, pageRequest); // 分页查询
  }

  @Override
  public List<T> searchAll() {
    Iterable<T> iterable = repository().findAll();
    return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public List<T> searchAll(T model) {
    log.info("全部搜索");
    RestModel<T> restModel = new RestModel<>();
    restModel.setModel(model);
    return searchAll(restModel);
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
    PersistFunctionUtils.validateUpdate(model);
    Field idField = EntityUtils.getIdField(model);
    Assert.notNull(idField, "无法获取更新对象主键：" + model.getClass());
    I id = EntityUtils.getFieldValue(model, idField);
    Assert.notNull(id, "更新对象主键不能为空：" + model.getClass());
    boolean exists = repository.existsById(id);
    Assert.isTrue(exists,
        String.format("更新对象不存在：%s.%s=%s", model.getClass(), idField.getName(), id));
    return repository.save(model);
  }

  @Override
  public <S extends T> S update(RestModel<S> restModel) {
    S model = restModel.getModel();
    return this.update(model);
  }

  @Override
  public <S extends T> S patch(S model) {
    PersistFunctionUtils.validateUpdate(model);
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
      PersistFunctionUtils.validateDelete(optional.get()); // 数据删除校验
      repository.deleteById(id);
    } else {
      log.warn("数据不存在：[id={}]", id);
    }
  }

  @Override
  public <S extends T> void delete(RestModel<S> restModel) {
    S model = restModel.getModel();
    PersistFunctionUtils.validateDelete(model); // 数据删除校验
    repository.delete(model);
  }

  @Override
  public void delete(Iterable<? extends I> ids) {
    for (I id : ids) {
      delete(id);
    }
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
