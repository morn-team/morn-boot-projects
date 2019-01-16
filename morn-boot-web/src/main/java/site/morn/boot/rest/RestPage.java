package site.morn.boot.rest;

import site.morn.core.CriteriaMap;
import site.morn.rest.RestModel;
import site.morn.rest.RestPageable;
import site.morn.util.TypeUtils;

/**
 * REST分页请求
 *
 * @author timely-rain
 * @see RestPageDefinition REST分页请求
 * @since 1.0.0, 2018/7/10
 */
public class RestPage<M> extends RestModel<M> implements
    RestPageDefinition<RestPageable, M, CriteriaMap> {

  /**
   * 分页参数
   */
  private RestPageable pageable;

  public RestPage() {
    super();
    pageable = new RestPageable();
  }

  @Override
  public RestPageable getPageable() {
    return pageable;
  }

  @Override
  public <T extends RestPageDefinition<RestPageable, M, CriteriaMap>> T setPageable(
      RestPageable pageable) {
    this.pageable = pageable;
    return TypeUtils.as(this);
  }
}
