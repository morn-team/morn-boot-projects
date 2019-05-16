package site.morn.log;

/**
 * 操作日志处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@FunctionalInterface
public interface OperationProcessor {

  /**
   * 处理操作日志
   *
   * @param meta 操作日志元数据
   * @param operation 操作日志实例
   */
  void handle(OperateMeta meta, Operation operation);
}
