package site.morn.bean.support;

import java.lang.annotation.Annotation;
import lombok.ToString;
import site.morn.bean.AnnotationFeature;

/**
 * 注解特性构建器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/18
 */
@ToString
public class AnnotationFeatureBuilder {

  /**
   * 名称
   *
   * @see site.morn.bean.annotation.Name
   * @see site.morn.bean.annotation.Function
   */
  private String name;

  /**
   * 标签
   *
   * @see site.morn.bean.annotation.Tag
   */
  private final Tags tags;

  /**
   * 源类型
   *
   * @see site.morn.bean.annotation.Source
   */
  private Class<?> source;

  /**
   * 目标类型
   *
   * @see site.morn.bean.annotation.Target
   */
  private Class<?> target;

  private AnnotationFeatureBuilder() {
    this.tags = Tags.empty();
  }

  /**
   * 设置名称
   */
  public AnnotationFeatureBuilder name(String name) {
    this.name = name;
    return this;
  }

  /**
   * 添加注解标签
   *
   * @param annotation 注解类
   * @param value 注解值
   */
  public AnnotationFeatureBuilder tag(Class<? extends Annotation> annotation, Object value) {
    this.tags.add(annotation, value);
    return this;
  }

  /**
   * 设置标签
   *
   * @param tags 标签数组
   */
  public AnnotationFeatureBuilder tags(String... tags) {
    this.tags.set(tags);
    return this;
  }

  public AnnotationFeatureBuilder source(Class<?> source) {
    this.source = source;
    return this;
  }

  public AnnotationFeatureBuilder target(Class<?> target) {
    this.target = target;
    return this;
  }

  public AnnotationFeature build() {
    return new SimpleAnnotationFeature(name, tags.toArray(), source, target);
  }

  /**
   * 创建空的注解特征构建器
   */
  public static AnnotationFeatureBuilder empty() {
    return new AnnotationFeatureBuilder();
  }

  public static AnnotationFeatureBuilder fromFeature(AnnotationFeature feature) {
    return new AnnotationFeatureBuilder()
        .name(feature.getName())
        .tags(feature.getTags())
        .source(feature.getSource())
        .target(feature.getTarget());
  }

  public static AnnotationFeatureBuilder withName(String name) {
    return new AnnotationFeatureBuilder().name(name);
  }

  public static AnnotationFeatureBuilder withTag(Class<? extends Annotation> annotation,
      Object value) {
    return new AnnotationFeatureBuilder().tag(annotation, value);
  }

  public static AnnotationFeatureBuilder withTags(String... tags) {
    return new AnnotationFeatureBuilder().tags(tags);
  }

  public static AnnotationFeatureBuilder withSource(Class<?> source) {
    return new AnnotationFeatureBuilder().source(source);
  }

  public static AnnotationFeatureBuilder withTarget(Class<?> target) {
    return new AnnotationFeatureBuilder().target(target);
  }
}
