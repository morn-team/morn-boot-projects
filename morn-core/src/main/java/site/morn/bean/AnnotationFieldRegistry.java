package site.morn.bean;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 注解属性注册表
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/17
 */
@Getter
public class AnnotationFieldRegistry {

  public static final String BEAN_ANNOTATION_REGISTRY = "beanAnnotationFieldRegistry";

  public static final String FUNCTION_ANNOTATION_REGISTRY = "functionAnnotationFieldRegistry";

  /**
   * 注解集合
   */
  private final List<AnnotationField> annotations = new ArrayList<>();

  /**
   * 添加实例注解
   *
   * @param annotationField 实例注解
   */
  public void add(AnnotationField annotationField) {
    annotations.add(annotationField);
  }

  /**
   * 添加注解
   *
   * @param annotation 注解类型
   */
  public void add(Class<? extends Annotation> annotation) {
    AnnotationField annotationField = new AnnotationField(annotation);
    add(annotationField);
  }

  /**
   * 添加注解
   *
   * @param annotation 注解类型
   */
  public void add(Class<? extends Annotation> annotation, AnnotationFieldType type) {
    AnnotationField annotationField = new AnnotationField(annotation);
    annotationField.setAnnotationType(type);
    add(annotationField);
  }
}
