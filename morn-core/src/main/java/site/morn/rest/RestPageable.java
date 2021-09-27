package site.morn.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import site.morn.util.GenericUtils;

/**
 * REST分页参数
 *
 * @author timely-rain
 * @since 1.0.0, 2018/7/10
 */
@ApiModel("REST分页参数")
public class RestPageable implements RestPageableDefinition {

  /**
   * 默认页数
   */
  public static final int DEFAULT_PAGE = 0;
  /**
   * 默认单页数量
   */
  public static final int DEFAULT_SIZE = 20;

  /**
   * 当前页
   */
  @ApiModelProperty(value = "当前页", example = "0")
  private int page;

  /**
   * 单页数量
   */
  @ApiModelProperty(value = "单页数量", example = "20")
  private int size;

  /**
   * 排序
   */
  @ApiModelProperty(value = "排序", example = "id desc")
  private String sort;

  public RestPageable() {
    this.page = DEFAULT_PAGE;
    this.size = DEFAULT_SIZE;
  }

  @Override
  public int getPage() {
    return page;
  }

  @Override
  public <T extends RestPageableDefinition> T setPage(int page) {
    this.page = page;
    return to();
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public <T extends RestPageableDefinition> T setSize(int size) {
    this.size = size;
    return to();
  }

  @Override
  public String getSort() {
    return sort;
  }

  @Override
  public <T extends RestPageableDefinition> T setSort(String sort) {
    this.sort = sort;
    return GenericUtils.castFrom(this);
  }

  @Override
  public <T extends RestPageableDefinition> T prevPage() {
    this.page -= 1;
    return to();
  }

  @Override
  public <T extends RestPageableDefinition> T nextPage() {
    this.page += 1;
    return to();
  }

  @SuppressWarnings("unchecked")
  private <T extends RestPageableDefinition> T to() {
    return (T) this;
  }
}
