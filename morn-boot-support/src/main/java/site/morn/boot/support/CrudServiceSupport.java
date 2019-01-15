package site.morn.boot.support;

import java.io.Serializable;
import javax.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import site.morn.boot.rest.RestPage;
import site.morn.core.CriteriaMap;
import site.morn.rest.RestModel;

/**
 * 基础服务实现
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
@Slf4j
@Getter
public abstract class CrudServiceSupport<T, I extends Serializable> implements CrudService<T, I> {

  @Resource
  protected JpaRepository<T, I> repository;

  @Override
  public <S extends T> S add(RestModel<S> restModel) {
    return null;
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
    return null;
  }

  @Override
  public <S extends T> S patch(RestModel<S> restModel) {
    return null;
  }

  @Override
  public void delete(I id) {

  }

  @Override
  public <S extends T> void delete(RestModel<S> restModel) {

  }

  @Override
  public void delete(Iterable<? extends I> ids) {

  }

  public abstract Specification<T> searchSpecification(T model, CriteriaMap attach);
}
