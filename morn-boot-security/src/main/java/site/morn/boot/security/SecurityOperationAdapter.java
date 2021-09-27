package site.morn.boot.security;

import static site.morn.util.AnnotationFeatureUtils.WILDCARD;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import site.morn.bean.annotation.Tag;
import site.morn.log.OperateMeta;
import site.morn.log.Operation;
import site.morn.log.OperationAdapter;

/**
 * 安全操作适配器
 *
 * @author timely-rain
 * @since 1.2.2, 2021/7/13
 */
@Order
@Tag(WILDCARD)
public class SecurityOperationAdapter implements OperationAdapter {

  @Override
  public void adaption(OperateMeta meta, Operation operation) {
    // 获取操作人
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return;
    }
    operation.setOperatorName(authentication.getName());
  }
}
