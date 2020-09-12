package site.morn.boot.data;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import site.morn.rest.RestModel;
import site.morn.rest.RestPage;

/**
 * 基础服务
 *
 * @author timely-rain
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
public interface CrudService<T, I extends Serializable> {

  /**
   * 获取
   *
   * @param id 主键
   * @return 持久化对象
   */
  T get(I id);

  /**
   * 新增
   *
   * @param model 业务模型
   * @param <S> 实体类型
   * @return 持久化对象
   */
  <S extends T> S add(S model);

  /**
   * 新增
   *
   * @param restModel REST模型
   * @param <S> 实体类型
   * @return 持久化对象
   */
  <S extends T> S add(RestModel<S> restModel);

  /**
   * 分页查询
   *
   * @param restPage REST分页请求
   * @return 分页结果集
   */
  Page<T> search(RestPage<T> restPage);

  /**
   * 全部查询
   *
   * @return 分页结果集
   */
  List<T> searchAll();

  /**
   * 全部查询
   *
   * @param model 业务模型
   * @return 分页结果集
   */
  List<T> searchAll(T model);

  /**
   * 全部查询
   *
   * @param restModel REST模型
   * @return 分页结果集
   */
  List<T> searchAll(RestModel<T> restModel);

  /**
   * 全量更新
   *
   * @param model 业务模型
   * @param <S> 实体类型
   * @return 持久化对象
   */
  <S extends T> S update(S model);

  /**
   * 全量更新
   *
   * @param restModel REST模型
   * @param <S> 实体类型
   * @return 持久化对象
   */
  <S extends T> S update(RestModel<S> restModel);

  /**
   * 增量更新
   *
   * @param model 业务模型
   * @param <S> 实体类型
   * @return 持久化对象
   */
  <S extends T> S patch(S model);

  /**
   * 增量更新
   *
   * @param restModel REST模型
   * @param <S> 实体类型
   * @return 持久化对象
   */
  <S extends T> S patch(RestModel<S> restModel);

  /**
   * 删除
   *
   * @param id 主键
   */
  void delete(I id);

  /**
   * 删除
   *
   * @param restModel REST模型
   */
  <S extends T> void delete(RestModel<S> restModel);

  /**
   * 批量删除
   *
   * @param ids 主键集合
   */
  void delete(Iterable<? extends I> ids);
}
