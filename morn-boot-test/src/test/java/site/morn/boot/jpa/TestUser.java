package site.morn.boot.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 * 测试用户
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/22
 */
@Data
@Entity
public class TestUser {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String username;

  @Column
  private String password;
}
