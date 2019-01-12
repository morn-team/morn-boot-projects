package site.morn.boot.rest;

import site.morn.core.CriteriaAttributes;
import site.morn.rest.RestPageableAttributes;

/**
 * REST分页请求
 *
 * @author timely-rain
 * @see RestPageAttributes REST分页请求
 * @since 1.0.0, 2018/7/10
 */
public class RestPage<M> implements RestPageAttributes<RestPageableAttributes, M> {

  /**
   * 分页参数
   */
  private RestPageableAttributes pageable;

  /**
   * 数据模型
   */
  private M model;

  /**
   * 附加数据
   */
  private CriteriaAttributes attach;

  public RestPage() {
    pageable = new RestPageable();
  }

  @Override
  public RestPageableAttributes getPageable() {
    return pageable;
  }

  @Override
  public RestPage<M> setPageable(RestPageableAttributes pageable) {
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
  public CriteriaAttributes getAttach() {
    return attach;
  }

  @Override
  public RestPage<M> setAttach(CriteriaAttributes attach) {
    this.attach = attach;
    return this;
  }
}
