package site.morn.boot.support;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import site.morn.boot.rest.RestPage;
import site.morn.rest.RestBuilders;
import site.morn.rest.RestMessage;
import site.morn.util.TypeUtils;

/**
 * 基础控制器实现
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/15
 */
public class CrudControllerSupport<T, I extends Serializable, S extends CrudService<T, I>>
    implements CrudController<T, I> {

  /**
   * 基础服务
   */
  @Autowired
  private CrudService<T, I> service;

  protected S service() {
    return TypeUtils.as(service);
  }

  /**
   * 搜索
   *
   * @param restPage REST分页参数
   * @return REST消息
   */
  @PostMapping("search")
  public RestMessage search(RestPage<T> restPage) {
    Page<T> page = service.search(restPage);
    return RestBuilders.successMessage(page);
  }
}
