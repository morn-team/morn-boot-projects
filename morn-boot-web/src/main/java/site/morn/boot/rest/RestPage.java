package site.morn.boot.rest;

/**
 * REST分页请求
 *
 * @author timely-rain
 * @version 1.0.0, 2018/7/10
 * @see RestPageAttributes REST分页请求
 * @since 1.0
 */
public class RestPage<M> implements RestPageAttributes<RestPageable, M> {

  /**
   * 分页参数
   */
  private RestPageable pageable;

  /**
   * 数据模型
   */
  private M model;

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
}
