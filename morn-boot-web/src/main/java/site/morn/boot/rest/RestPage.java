package site.morn.boot.rest;

import io.swagger.annotations.ApiModel;
import site.morn.core.CriteriaMap;
import site.morn.rest.RestModel;
import site.morn.util.GenericUtils;

/**
 * REST分页请求
 *
 * @author timely-rain
 * @see RestPageDefinition REST分页请求
 * @since 1.0.0, 2018/7/10
 */
@ApiModel(value = "REST分页模型", description = "统一数据模型，通常用于请求体")
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
    return GenericUtils.castFrom(this);
  }
}
