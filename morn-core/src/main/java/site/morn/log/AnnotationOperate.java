package site.morn.log;

/**
 * 注解操作
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
public interface AnnotationOperate {

  /**
   * 获取操作模块
   *
   * @return 操作模块
   */
  String getModule();

  /**
   * 获取操作名称
   *
   * @return 操作名称
   */
  String getName();

  /**
   * 例外异常类型
   */
  Class<? extends Throwable>[] getExcepts();
}
