package site.morn.boot.support.mongo;

import java.io.Serializable;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.morn.boot.rest.RestPage;
import site.morn.boot.support.CrudService;
import site.morn.rest.RestModel;
import site.morn.util.TypeUtils;
import site.morn.validate.persistent.PersistValidateUtils;

/**
 * Mongo基础服务实现
 *
 * @author TianGanLin
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
    return TypeUtils.as(repository);
  }

  @Override
  public <S extends T> S add(RestModel<S> restModel) {
    S model = restModel.getModel();
    return repository.save(model);
  }

  @Override
  public Page<T> search(RestPage<T> restPage) {
    log.info("分页搜索");
    PageRequest pageRequest = restPage.generatePageRequest();// 分页请求
    return repository.findAll(pageRequest); // 分页查询
  }

  @Override
  public List<T> searchAll(RestModel<T> restModel) {
    log.info("全部搜索");
    return repository.findAll(); // 分页查询
  }

  @Override
  public <S extends T> S update(RestModel<S> restModel) {
    S model = restModel.getModel();
    return repository.save(model);
  }

  @Override
  public <S extends T> S patch(RestModel<S> restModel) {
    S model = restModel.getModel();
    return repository.save(model);
  }

  @Override
  public void delete(I id) {
    T model = repository().findOne(id);
    PersistValidateUtils.validateDelete(model); // 数据删除校验
    repository.delete(id);
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
}
