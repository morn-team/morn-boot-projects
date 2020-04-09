package site.morn.test;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
import site.morn.validate.group.Add;

/**
 * 测试用户
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/22
 */
@Data
@Entity
public class TestUser implements Serializable {


  @Id
  @GeneratedValue
  private Long id;

  @Column
  @NotNull
  private String username;

  @Column
  @NotNull(groups = Add.class)
  private String password;
}
