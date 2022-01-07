package site.morn.boot.data.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 树形映射类
 *
 * @author timely-rain
 * @since 0.0.1, 2019/4/4 0004
 */
@Getter
@Setter
@MappedSuperclass
public class TreeEntity<M extends TreeEntity<M, I>, I extends Serializable> {

  /**
   * 主键
   */
  @Id
  @GeneratedValue
  protected I id;

  /**
   * 父级编号
   */
  @Column
  protected I parentId;

  /**
   * 层级
   */
  @Column
  protected Integer level;

  /**
   * 检索编码
   */
  @Column
  protected String levelCode;

  /**
   * 父级节点
   */
  @Transient
  protected M parent;

  /**
   * 持久化前执行
   */
  @PrePersist
  public void beforeSave() {
    generateParentId();
    generateSearchCode();
  }

  /**
   * 生成检索编码
   */
  private void generateParentId() {
    if (Objects.nonNull(parentId) || Objects.isNull(parent)) {
      return;
    }
    parentId = parent.id;
  }

  /**
   * 生成检索编码
   */
  private void generateSearchCode() {
    if (!StringUtils.isEmpty(levelCode)) {
      return;
    }
    if (Objects.isNull(parent) || StringUtils.isEmpty(parent.levelCode)) {
      levelCode = String.format("|%s|", id);
    } else {
      levelCode = String.format("%s%s|", parent.levelCode, id);
    }
  }
}
