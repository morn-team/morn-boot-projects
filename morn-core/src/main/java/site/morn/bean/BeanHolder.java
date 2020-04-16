package site.morn.bean;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.morn.bean.support.SimpleAnnotationFeature;

/**
 * 标识的实例持有者
 *
 * @param <T> 实例类型
 * @author timely-rain
 * @since 1.0.0, 2018/11/26
 */
@NoArgsConstructor
@Getter
@Setter
public class BeanHolder<T> extends SimpleAnnotationFeature implements AnnotationFeature {

  /**
   * 实例
   */
  private T bean;

  /**
   * 方法集合
   */
  private List<FunctionHolder> functionHolders;

  public BeanHolder(AnnotationFeature identify, T bean,
      List<FunctionHolder> functionHolders) {
    super(identify);
    this.bean = bean;
    this.functionHolders = functionHolders;
  }
}
