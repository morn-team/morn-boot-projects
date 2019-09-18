package site.morn.boot.data.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 创建者映射类
 *
 * @author timely-rain
 * @since 0.0.1, 2019/4/4 0004
 */
@Getter
@Setter
@MappedSuperclass
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
public class CreatorEntity {

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  @Column(length = 32)
  @CreatedBy
  protected String creator;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @Column
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  protected Date createTime;
}
