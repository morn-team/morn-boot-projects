package site.morn.boot.support.entity;

import java.awt.event.AdjustmentListener;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * 创建映射类
 *
 * @author timely-rain
 * @since 0.0.1, 2019/4/4 0004
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AdjustmentListener.class)
public class CreatedEntity {

  /**
   * 创建时间
   */
  @Column
  @CreatedDate
  protected Date createTime;

  /**
   * 修改时间
   */
  @Column
  @LastModifiedDate
  protected Date modifyTime;
}
