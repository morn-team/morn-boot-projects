package site.morn.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 注解标识实现
 *
 * <p>注解标识用于描述对象的唯一标识、标签、用途
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 * @since 2.1.0, 2019/5/18
 */
@Builder
@Getter
@Setter
@ToString
public class AnnotationIdentifyCase implements AnnotationIdentify {

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
   * 目标类型
   *
   * @see site.morn.bean.annotation.Target
   */
  private Class<?> target;
}
