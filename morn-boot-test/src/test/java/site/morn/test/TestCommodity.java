package site.morn.test;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 * 商品
 *
 * @author timely-rain
 * @since 1.2.2, 2020/8/20
 */
@Data
@Entity
public class TestCommodity {

  @Id
  @GeneratedValue
  private Long id;

  /**
   * 商品类型
   */
  @Column
  private Integer type;

  /**
   * 商品名称
   */
  @Column
  private String name;

  /**
   * 配料
   */
  @Column
  private String ingredients;

  /**
   * 商品价格
   */
  @Column
  private Float price;
}
