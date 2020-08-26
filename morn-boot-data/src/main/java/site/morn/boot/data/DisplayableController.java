package site.morn.boot.data;

import io.swagger.annotations.ApiOperation;
import java.io.Serializable;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.morn.boot.rest.RestBuilders;
import site.morn.log.OperateAction;
import site.morn.log.OperateArguments;
import site.morn.rest.RestMessage;

/**
 * 显示/隐藏控制器
 *
 * @author timely-rain
 * @since 2.1.1, 2019/8/15
 */
public class DisplayableController<T, I extends Serializable, S extends CrudService<T, I> & DisplayableService<T, I>> extends
    CrudController<T, I, S> {

  /**
   * 显示
   */
  @ApiOperation("显示")
  @OperateAction("display")
  @PutMapping("display")
  public RestMessage display(@RequestBody List<I> ids) {
    OperateArguments.add(ids);
    service().displayAll(ids);
    return RestBuilders.successMessage();
  }

  /**
   * 隐藏
   */
  @ApiOperation("隐藏")
  @OperateAction("hide")
  @PutMapping("hide")
  public RestMessage hide(@RequestBody List<I> ids) {
    OperateArguments.add(ids);
    service().hideAll(ids);
    return RestBuilders.successMessage();
  }
}
