package site.morn.boot.data.mongo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.morn.boot.data.CrudService;
import site.morn.boot.data.rest.RestPageUtils;
import site.morn.rest.RestModel;
import site.morn.rest.RestPage;
import site.morn.util.GenericUtils;
import site.morn.util.PersistFunctionUtils;

/**
 * Mongo基础服务实现
 *
 * @author timely-rain
 * @since 1.0.2, 2019/5/9
 */
@Slf4j
public class MongoCrudService<T, I extends Serializable, R extends MongoRepository<T, I>>
    implements CrudService<T, I> {

  @Autowired
  private MongoRepository<T, I> repository;

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
    RestModel<S> restModel = new RestModel<>();
    restModel.setModel(model);
    return add(restModel);
  }

  @Override
  public <S extends T> S add(RestModel<S> restModel) {
    S model = restModel.getModel();
    PersistFunctionUtils.validateAdd(model);
    return repository.save(model);
  }

  @Override
  public Page<T> search(RestPage<T> restPage) {
    log.info("分页搜索");
    PageRequest pageRequest = RestPageUtils.generatePageRequest(restPage);// 分页请求
    return repository.findAll(pageRequest); // 分页查询
  }

  @Override
  public List<T> searchAll() {
    return repository.findAll();
  }

  @Override
  public List<T> searchAll(T model) {
    log.info("全部搜索");
    return repository.findAll(); // 分页查询
  }

  @Override
  public List<T> searchAll(RestModel<T> restModel) {
    log.info("全部搜索");
    return repository.findAll(); // 分页查询
  }

  @Override
  public <S extends T> S update(S model) {
    RestModel<S> restModel = new RestModel<>();
    restModel.setModel(model);
    return update(restModel);
  }

  @Override
  public <S extends T> S update(RestModel<S> restModel) {
    S model = restModel.getModel();
    PersistFunctionUtils.validateUpdate(model);
    return repository.save(model);
  }

  @Override
  public <S extends T> S patch(S model) {
    RestModel<S> restModel = new RestModel<>();
    restModel.setModel(model);
    return patch(restModel);
  }

  @Override
  public <S extends T> S patch(RestModel<S> restModel) {
    S model = restModel.getModel();
    PersistFunctionUtils.validateUpdate(model);
    return repository.save(model);
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
    throw new UnsupportedOperationException();
  }
}
