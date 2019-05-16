package site.morn.log;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.morn.core.CriteriaMap;

/**
 * 操作元数据
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Builder
@Getter
@Setter
@ToString
public class OperateMeta implements AnnotationOperate {

  /**
   * 日志来源，通常是切点
   */
  private Object source;

  /**
   * 操作成功标识
   */
  private boolean success;

  /**
   * 操作模块
   */
  private String module;

  /**
   * 操作名称
   */
  private String name;

  /**
   * 操作参数
   */
  private Object[] arguments;

  /**
   * 方法参数
   */
  private Object[] methodArgs;

  /**
   * 方法返回值
   */
  private Object methodReturned;

  /**
   * 例外异常类型
   */
  private Class<? extends Throwable>[] excepts;

  /**
   * 附加数据
   */
  private CriteriaMap attach;
}
