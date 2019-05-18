package site.morn.bean;

import java.lang.reflect.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 方法持有者
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/17
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FunctionHolder {

  /**
   * 名称
   *
   * <p>同类中不允许名称重复
   */
  private String name;

  /**
   * 标签
   *
   * <p>同类中允许标签重复
   */
  private String[] tags;

  /**
   * 方法
   */
  private Method method;

  /**
   * 参数类型
   */
  private Class<?>[] parameterTypes;
}
