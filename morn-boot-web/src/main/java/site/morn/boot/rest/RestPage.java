package site.morn.boot.rest;

import site.morn.core.CriteriaMap;

/**
 * REST分页请求
 *
 * @author timely-rain
 * @see RestPageAttributes REST分页请求
 * @since 1.0.0, 2018/7/10
 */
public class RestPage<M> implements RestPageAttributes<RestPageable, M, CriteriaMap> {

  /**
   * 分页参数
   */
  private RestPageable pageable;

  /**
   * 数据模型
   */
  private M model;

  /**
   * 附加数据
   */
  private CriteriaMap attach;

  public RestPage() {
    pageable = new RestPageable();
  }

  @Override
  public RestPageable getPageable() {
    return pageable;
  }

  @Override
  public RestPage<M> setPageable(RestPageable pageable) {
    this.pageable = pageable;
    return this;
  }

  @Override
  public M getModel() {
    return model;
  }

  @Override
  public RestPage<M> setModel(M model) {
    this.model = model;
    return this;
  }

  @Override
  public CriteriaMap getAttach() {
    return attach;
  }

  @Override
  public RestPage<M> setAttach(CriteriaMap attach) {
    this.attach = attach;
    return this;
  }
}
