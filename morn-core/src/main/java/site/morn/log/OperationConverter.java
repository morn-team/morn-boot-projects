package site.morn.log;

import java.util.Date;
import site.morn.core.BeanConverter;

/**
 * 操作日志转换器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
public interface OperationConverter extends BeanConverter<OperateMeta, Operation> {

  /**
   * 构建操作实例
   *
   * @param operateMeta 操作元数据
   * @param content     日志内容
   * @return 操作实例
   */
  default Operation buildOperation(OperateMeta operateMeta, String content) {
    // 构建操作日志实例
    Operation operation = new Operation();
    operation.setSuccess(operateMeta.isSuccess());
    operation.setModule(operateMeta.getGroupName());
    operation.setName(operateMeta.getActionName());
    operation.setContent(content);
    operation.setDate(new Date(operateMeta.getStartTime()));
    operation.setDuration((int) operateMeta.getDuration());
    return operation;
  }
}
