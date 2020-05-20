package site.morn.log;

import static site.morn.log.OperateModes.SIMPLE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.experimental.UtilityClass;

/**
 * 操作行为
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperateAction {

  /**
   * 操作名称
   */
  String value();

  /**
   * 模式
   * <p>用于区分如何解析、处理日志</p>
   *
   * @see OperateModes
   */
  String mode() default SIMPLE;

  /**
   * 操作参数
   */
  String[] args() default {};

  /**
   * 例外异常类型
   */
  Class<? extends Throwable>[] excepts() default {};

  /**
   * 排除的属性名称
   */
  String[] excludeNames() default {};

  @UtilityClass
  class Actions {

    /**
     * 新增
     */
    public static final String ADD = "add";

    /**
     * 修改
     */
    public static final String DELETE = "delete";

    /**
     * 删除
     */
    public static final String UPDATE = "update";
  }
}
