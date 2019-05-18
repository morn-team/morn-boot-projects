package site.morn.bean;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 实例注解注册表
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/17
 */
@Getter
public class BeanAnnotationRegistry {

  /**
   * 注解集合
   */
  private final List<BeanAnnotation> annotations = new ArrayList<>();

  /**
   * 添加实例注解
   *
   * @param beanAnnotation 实例注解
   */
  public void add(BeanAnnotation beanAnnotation) {
    annotations.add(beanAnnotation);
  }
}
