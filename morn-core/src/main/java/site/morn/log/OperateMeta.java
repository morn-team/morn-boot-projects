package site.morn.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.morn.core.CriteriaMap;
import site.morn.core.X;

/**
 * 操作元数据
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Getter
@Setter
@ToString
public class OperateMeta {

  /**
   * 日志来源，通常是切点
   */
  private Object source;

  /**
   * 请求对象
   */
  private X request;

  /**
   * 响应对象
   */
  private X response;

  /**
   * 操作成功标识
   */
  private boolean success;

  /**
   * 操作模块
   */
  private String groupName;

  /**
   * 操作名称
   */
  private String actionName;

  /**
   * 模块参数
   */
  private Object[] groupArgs;

  /**
   * 行为参数
   */
  private Object[] actionArgs;

  /**
   * 实时参数
   */
  private Object[] codeArgs;

  /**
   * 方法参数
   */
  private Object[] methodArgs;

  /**
   * 方法返回值
   */
  private Object methodReturned;

  /**
   * 异常
   */
  private Throwable throwable;

  /**
   * 操作起始时间
   */
  private long startTime;

  /**
   * 操作结束时间
   */
  private long endTime;

  /**
   * 操作消耗时间
   */
  private long duration;

  /**
   * 例外异常类型
   */
  private Class<? extends Throwable>[] excepts;

  /**
   * 排除的属性名称
   */
  private String[] excludeNames;

  /**
   * 附加数据
   */
  private CriteriaMap attach;
}
