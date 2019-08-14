package site.morn.data;

/**
 * 持久化树形节点
 *
 * @author timely-rain
 * @since 2.1.0, 2019/8/13
 */
public interface PersistTreeNode {

  /**
   * 获取查询码
   */
  String getSearchCode();

  /**
   * 设置查询码
   */
  void setSearchCode(String searchCode);
}
