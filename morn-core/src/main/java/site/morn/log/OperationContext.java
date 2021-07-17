package site.morn.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 操作上下文
 *
 * @author timely-rain
 * @since 1.2.2, 2021/7/12
 */
@Getter
@RequiredArgsConstructor
public class OperationContext {

  /**
   * 日志元数据
   */
  private final OperateMeta meta;

  /**
   * 操作实例
   */
  private final Operation operation;
}
