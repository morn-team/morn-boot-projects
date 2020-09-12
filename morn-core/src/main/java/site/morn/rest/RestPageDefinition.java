package site.morn.rest;

import site.morn.core.CriteriaAttributes;

/**
 * REST分页请求
 *
 * @author timely-rain
 * @since 1.0.0, 2018/7/10
 */
public interface RestPageDefinition<P extends RestPageableDefinition, M, A extends CriteriaAttributes>
    extends RestModelDefinition<M, A> {

  /**
   * 获取REST分页参数
   *
   * @return REST分页参数
   */
  P getPageable();

  /**
   * 设置REST分页参数
   *
   * @param pageable 分页参数
   * @return REST分页请求
   */
  <T extends RestPageDefinition<P, M, A>> T setPageable(P pageable);
}
