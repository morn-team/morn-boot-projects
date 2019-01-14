package site.morn.boot.rest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import site.morn.core.CriteriaAttributes;
import site.morn.rest.RestModelDefinition;
import site.morn.rest.RestPageableDefinition;

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

  /**
   * 生成JPA分页请求
   *
   * @return JPA分页请求
   */
  default PageRequest generatePageRequest() {
    RestPageableDefinition pageable = getPageable();
    return new PageRequest(pageable.getPage(), pageable.getSize());
  }

  /**
   * 生成JPA分页请求
   *
   * @return JPA分页请求
   */
  default PageRequest generatePageRequest(Sort sort) {
    RestPageableDefinition pageable = getPageable();
    return new PageRequest(pageable.getPage(), pageable.getSize(), sort);
  }
}
