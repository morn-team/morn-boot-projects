package site.morn.boot.rest;

import site.morn.rest.RestPageableAttributes;

/**
 * REST分页参数
 *
 * @author timely-rain
 * @since 1.0.0, 2018/7/10
 */
public class RestPageable implements RestPageableAttributes {

  /**
   * 当前页
   */
  private int page;

  /**
   * 单页数量
   */
  private int size;

  public RestPageable() {
    this.page = DEFAULT_PAGE;
    this.size = DEFAULT_SIZE;
  }

  @Override
  public int getPage() {
    return page;
  }

  @Override
  public <T extends RestPageableAttributes> T setPage(int page) {
    this.page = page;
    return to();
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public <T extends RestPageableAttributes> T setSize(int size) {
    this.size = size;
    return to();
  }

  @Override
  public <T extends RestPageableAttributes> T prevPage() {
    this.page -= 1;
    return to();
  }

  @Override
  public <T extends RestPageableAttributes> T nextPage() {
    this.page += 1;
    return to();
  }

  @SuppressWarnings("unchecked")
  private <T extends RestPageableAttributes> T to() {
    return (T) this;
  }
}
