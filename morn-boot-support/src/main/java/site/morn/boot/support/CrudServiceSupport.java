package site.morn.boot.support;

import java.io.Serializable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import site.morn.boot.rest.RestPage;
import site.morn.core.CriteriaMap;
import site.morn.rest.RestModel;
import site.morn.util.TypeUtils;

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
  public <S extends T> S add(RestModel<S> restModel) {
    S model = restModel.getModel();
    return repository.save(model);
  }

  @Override
  public Page<T> search(RestPage<T> restPage) {
    log.info("搜索列表");
    PageRequest pageRequest = restPage.generatePageRequest();// 分页请求
    CriteriaMap attach = restPage.getAttach(); // 附加数据
    T model = restPage.getModel(); // 数据模型
    Specification<T> specification = searchSpecification(model, attach);// 查询条件
    return repository.findAll(specification, pageRequest); // 分页查询
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
    repository.delete(id);
  }

  @Override
  public <S extends T> void delete(RestModel<S> restModel) {
    S model = restModel.getModel();
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
  protected abstract Specification<T> searchSpecification(T model, CriteriaMap attach);
}
