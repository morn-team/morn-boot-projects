package site.morn.log;

import site.morn.core.BeanAdapter;

/**
 * 操作适配器
 *
 * @author timely-rain
 * @since 1.2.2, 2021/7/12
 */
public interface OperationAdapter extends BeanAdapter<OperationContext> {

  /**
   * 适配
   *
   * @param meta      元数据
   * @param operation 操作实例
   */
  void adaption(OperateMeta meta, Operation operation);

  default OperationContext adaption(OperationContext context) {
    adaption(context.getMeta(), context.getOperation());
    return context;
  }
}
