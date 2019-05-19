package site.morn.bean;

import java.lang.annotation.Annotation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * 实例注解
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/17
 */
@RequiredArgsConstructor
@Getter
@Setter
public class BeanAnnotation {

  /**
   * 注解类型
   */
  private final Class<? extends Annotation> annotationType;

  /**
   * 注解属性名称
   */
  private String attributeName = AnnotationUtils.VALUE;

  /**
   * 标签名称
   */
  private String tagName;
}
