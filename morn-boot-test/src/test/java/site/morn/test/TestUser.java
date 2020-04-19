package site.morn.test;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class TestUser implements Serializable {


  @Id
  @GeneratedValue
  private Long id;

  /**
   * 部门编号
   *
   * @see TestDepartment#getId()
   */
  @Column
  private Long departmentId;

  @Column
  @NotNull
  private String username;

  @Column
  @NotNull(groups = Add.class)
  private String password;
}
