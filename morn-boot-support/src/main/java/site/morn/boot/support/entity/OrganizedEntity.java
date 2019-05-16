package site.morn.boot.support.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 组织成员映射类
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/16
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class OrganizedEntity extends ReviserEntity {

  /**
   * 组织编号
   */
  @Column
  private Long departmentId;
}
