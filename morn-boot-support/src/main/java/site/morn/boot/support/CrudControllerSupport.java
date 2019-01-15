package site.morn.boot.support;

import java.io.Serializable;
import javax.annotation.Resource;

/**
 * 基础控制器实现
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/15
 */
public class CrudControllerSupport<T, I extends Serializable> implements CrudController<T, I> {

  @Resource
  protected CrudService<T, I> service;
}
