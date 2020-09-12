package site.morn.rest;


import site.morn.core.CriteriaAttributes;

/**
 * REST分页参数
 *
 * @author timely-rain
 * @see CriteriaAttributes 标准属性类
 * @since 1.0.0, 2018/7/10
 */
public interface RestPageableDefinition {

  /**
   * 获取当前页
   *
   * @return 当前页
   */
  int getPage();

  /**
   * 设置当前页
   *
   * @param page 页码
   * @param <T> REST分页类型
   * @return REST分页参数
   */
  <T extends RestPageableDefinition> T setPage(int page);

  /**
   * 获取单页数量
   *
   * @return 单页数量
   */
  int getSize();

  /**
   * 设置单页数量
   *
   * @param size 单页数量
   * @param <T> REST分页类型
   * @return REST分页参数
   */
  <T extends RestPageableDefinition> T setSize(int size);

  /**
   * 获取排序
   *
   * @return 排序
   */
  String getSort();

  /**
   * 设置排序
   *
   * @param sort 排序
   * @param <T> REST分页类型
   * @return REST分页参数
   */
  <T extends RestPageableDefinition> T setSort(String sort);

  /**
   * 上一页
   */
  <T extends RestPageableDefinition> T prevPage();

  /**
   * 下一页
   */
  <T extends RestPageableDefinition> T nextPage();
}
