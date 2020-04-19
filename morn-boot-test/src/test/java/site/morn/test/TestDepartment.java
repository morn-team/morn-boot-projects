package site.morn.test;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 测试机构
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/18
 */
@Data
@AllArgsConstructor
public class TestDepartment {

  @Id
  @GeneratedValue
  private Long id;

  /**
   * 状态
   *
   * @apiNote <p>-1:初始化</p>
   * <p>0:删除处理</p>
   * <p>1:新增处理</p>
   * <p>2:更新处理</p>
   */
  @Column
  private int state;
}
