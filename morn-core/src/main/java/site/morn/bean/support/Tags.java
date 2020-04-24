package site.morn.bean.support;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import site.morn.util.AnnotationFeatureUtils;

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
  private final List<String> tagList = new ArrayList<>();

  private Tags() {
  }

  /**
   * 创建空的标签构建类
   */
  public static Tags empty() {
    return new Tags();
  }

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
    return empty().add(annotation, value);
  }

  /**
   * 通过标签名称构建
   *
   * @param tagName 标签名称
   * @param value 标签值
   * @return 标签构建类
   */
  public static Tags from(String tagName, Object value) {
    return empty().add(tagName, value);
  }

  /**
   * 通过标签名称构建
   *
   * @param value 标签值
   * @return 标签构建类
   */
  public static Tags from(Object value) {
    return from((String) null, value);
  }

  /**
   * 添加标签
   *
   * @param annotation 标签注解
   * @return 标签构建类
   */
  public Tags add(Class<? extends Annotation> annotation) {
    String tag = AnnotationFeatureUtils.getTag(annotation);
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
    String tag = AnnotationFeatureUtils.getTag(annotation, value);
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
    String tag = AnnotationFeatureUtils.getTag(tagName, value);
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
   * 设置标签
   *
   * @param tags 标签数组
   * @return 标签构建类
   */
  public Tags set(String... tags) {
    tagList.clear();
    List<String> strings = Arrays.stream(tags).collect(Collectors.toList());
    tagList.addAll(strings);
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
