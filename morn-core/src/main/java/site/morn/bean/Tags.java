package site.morn.bean;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import site.morn.util.AnnotationIdentifyUtils;

/**
 * 标签构建类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/19
 */
public class Tags {

  /**
   * 标签集合
   */
  private List<String> tagList = new ArrayList<>();

  /**
   * 通过注解构建
   *
   * @param annotation 标签注解
   * @return 标签构建类
   */
  public static Tags from(Class<? extends Annotation> annotation) {
    return from(annotation, null);
  }

  /**
   * 通过注解构建
   *
   * @param annotation 标签注解
   * @param value 标签值
   * @return 标签构建类
   */
  public static Tags from(Class<? extends Annotation> annotation, Object value) {
    Tags tags = new Tags();
    tags.add(annotation, value);
    return tags;
  }

  /**
   * 通过标签名称构建
   *
   * @param tagName 标签名称
   * @param value 标签值
   * @return 标签构建类
   */
  public static Tags from(String tagName, Object value) {
    Tags tags = new Tags();
    tags.add(tagName, value);
    return tags;
  }

  /**
   * 添加标签
   *
   * @param annotation 标签注解
   * @return 标签构建类
   */
  public Tags add(Class<? extends Annotation> annotation) {
    String tag = AnnotationIdentifyUtils.getTag(annotation);
    return add(tag);
  }

  /**
   * 添加标签
   *
   * @param annotation 标签注解
   * @param value 标签值
   * @return 标签构建类
   */
  public Tags add(Class<? extends Annotation> annotation, Object value) {
    String tag = AnnotationIdentifyUtils.getTag(annotation, value);
    return add(tag);
  }

  /**
   * 添加标签
   *
   * @param tagName 标签名称
   * @param value 标签值
   * @return 标签构建类
   */
  public Tags add(String tagName, Object value) {
    String tag = AnnotationIdentifyUtils.getTag(tagName, value);
    return add(tag);
  }

  /**
   * 添加标签
   *
   * @param tag 标签
   * @return 标签构建类
   */
  public Tags add(String tag) {
    tagList.add(tag);
    return this;
  }

  /**
   * 输出标签数组
   *
   * @return 标签数组
   */
  public String[] toArray() {
    return tagList.toArray(new String[0]);
  }
}
