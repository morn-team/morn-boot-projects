package site.morn.boot.data;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.morn.boot.rest.RestBuilders;
import site.morn.data.group.Add;
import site.morn.data.group.Delete;
import site.morn.data.group.Put;
import site.morn.data.group.Search;
import site.morn.data.group.Update;
import site.morn.log.OperateAction;
import site.morn.log.OperateAction.ActionConstants;
import site.morn.rest.RestMessage;
import site.morn.rest.RestModel;
import site.morn.rest.RestPage;
import site.morn.util.GenericUtils;

/**
 * 基础控制器实现
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/15
 */
public class CrudController<T, I extends Serializable, S extends CrudService<T, I>> {

  /**
   * 基础服务
   */
  @Autowired
  private CrudService<T, I> service;

  protected S service() {
    return GenericUtils.castFrom(service);
  }

  /**
   * 查询
   */
  @ApiOperation("单体查询")
  @ApiImplicitParam(name = "id", value = "主键")
  @GetMapping("{id}")
  public RestMessage get(@PathVariable I id) {
    T model = service().get(id);
    return RestBuilders.successMessage(model);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @OperateAction(ActionConstants.ADD)
  @PostMapping
  public RestMessage add(@Validated(Add.class) @RequestBody RestModel<T> restModel) {
    T model = service().add(restModel);
    return RestBuilders.successMessage(model);
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @OperateAction(ActionConstants.UPDATE)
  @PutMapping
  public RestMessage update(
      @Validated({Update.class, Put.class}) @RequestBody RestModel<T> restModel) {
    T model = service().update(restModel);
    return RestBuilders.successMessage(model);
  }

  /**
   * 搜索
   *
   * @param restPage REST分页参数
   * @return REST消息
   */
  @ApiOperation("分页搜索")
  @PostMapping("search")
  public RestMessage search(@Validated(Search.class) @RequestBody RestPage<T> restPage) {
    Page<T> page = service.search(restPage);
    return RestBuilders.successMessage(page);
  }

  /**
   * 删除
   */
  @ApiOperation("删除")
  @ApiImplicitParam(name = "id", value = "主键")
  @OperateAction(ActionConstants.DELETE)
  @DeleteMapping("/{id}")
  public RestMessage delete(@Validated({Delete.class}) @PathVariable I id) {
    service().delete(id);
    return RestBuilders.successMessage();
  }
}
