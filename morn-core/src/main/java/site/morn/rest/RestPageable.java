package site.morn.rest;

import site.morn.util.TypeUtils;

/**
 * REST分页参数
 *
 * @author timely-rain
 * @since 1.0.0, 2018/7/10
 */
public class RestPageable implements RestPageableDefinition {

  /**
   * 当前页
   */
  private int page;

  /**
   * 单页数量
   */
  private int size;

  /**
   * 排序
   */
  private String sort;

  public RestPageable() {
    this.page = RestPageableConstant.DEFAULT_PAGE;
    this.size = RestPageableConstant.DEFAULT_SIZE;
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
    return TypeUtils.as(this);
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
