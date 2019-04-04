package site.morn.boot.support.entity;

import java.awt.event.AdjustmentListener;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * 创建人映射类
 *
 * @author timely-rain
 * @since 0.0.1, 2019/4/4 0004
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AdjustmentListener.class)
public class CreatorEntity extends CreatedEntity {

  /**
   * 创建人
   */
  @Column(length = 32)
  @CreatedBy
  protected String creator;

  /**
   * 修改人
   */
  @Column(length = 32)
  @LastModifiedBy
  protected String modifier;
}
