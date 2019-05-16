package site.morn.boot.support.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * 组织成员映射类
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/16
 */
@Getter
@Setter
@FieldNameConstants
@MappedSuperclass
public class OrganizedEntity extends ReviserEntity {

  /**
   * 组织编号
   */
  @Column
  protected Long departmentId;
}
