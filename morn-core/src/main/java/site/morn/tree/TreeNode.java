package site.morn.tree;

/**
 * 树形节点
 *
 * @author timely-rain
 * @since 2.1.0, 2019/8/13
 */
public interface TreeNode {

  Long getId();

  Long getParentId();

  String getName();

  Integer getLevel();

  void setId(Long id);

  void setParentId(Long parentId);

  void setName(String name);

  void setLevel(Integer level);
}
