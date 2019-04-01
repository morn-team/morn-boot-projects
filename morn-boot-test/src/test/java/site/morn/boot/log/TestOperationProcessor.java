package site.morn.boot.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Tag;
import site.morn.log.OperateMeta;
import site.morn.log.Operation;
import site.morn.log.OperationProcessor;

/**
 * 测试操作处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/5
 */
@Component
@Slf4j
@Tag
public class TestOperationProcessor implements OperationProcessor {

  @Override
  public void handle(OperateMeta operateMeta, Operation operation) {
    log.info("保存操作日志：" + operation);
  }
}
