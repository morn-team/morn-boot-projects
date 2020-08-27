package site.morn.test;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.morn.boot.data.entity.OrganizedEntity;
import site.morn.data.Displayable;
import site.morn.data.group.Add;

/**
 * 测试用户
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/22
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TestUser extends OrganizedEntity implements Displayable, Serializable {


  @Id
  private Long id;

  @Column
  @NotNull
  private String username;

  @Column
  @NotNull(groups = Add.class)
  private String password;

  @Column
  private Boolean display;

  public TestUser(Long id, @NotNull String username) {
    this.id = id;
    this.username = username;
  }
}
