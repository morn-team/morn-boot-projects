package site.morn.boot.log;

import static site.morn.util.AnnotationFeatureUtils.WILDCARD;

import java.util.Date;
import org.springframework.core.annotation.Order;
import site.morn.bean.annotation.Tag;
import site.morn.log.OperateMeta;
import site.morn.log.Operation;
import site.morn.log.OperationAdapter;

/**
 * 基础操作适配器
 *
 * @author timely-rain
 * @since 1.2.2, 2021/7/14
 */
@Order
@Tag(WILDCARD)
public class SimpleOperationAdapter implements OperationAdapter {

  @Override
  public void adaption(OperateMeta meta, Operation operation) {
    operation.setSuccess(meta.isSuccess());
    operation.setModule(meta.getGroupName());
    operation.setName(meta.getActionName());
    operation.setDate(new Date(meta.getStartTime()));
    operation.setDuration((int) meta.getDuration());
  }
}
