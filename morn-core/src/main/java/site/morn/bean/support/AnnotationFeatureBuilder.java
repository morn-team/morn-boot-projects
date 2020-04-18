package site.morn.bean.support;

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
  private String[] tags;

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
  }

  public AnnotationFeatureBuilder name(String name) {
    this.name = name;
    return this;
  }

  public AnnotationFeatureBuilder tags(String[] tags) {
    this.tags = tags;
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
    return new SimpleAnnotationFeature(name, tags, source, target);
  }

  /**
   * 获取注解特征构建器
   *
   * @return 注解特征构建器
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
