package site.morn.boot.log;

import static site.morn.boot.log.OperateAspectTest.DONE;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Objective;
import site.morn.boot.json.util.JsonParsers;
import site.morn.log.OperateMeta;
import site.morn.log.Operation;
import site.morn.log.OperationProcessor;

/**
 * 测试操作处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/5
 */
@Slf4j
@Component
@Objective
public class TestOperationProcessor implements OperationProcessor {

  @Override
  public void handle(OperateMeta operateMeta, Operation operation) {
    HttpServletRequest request = operateMeta.getRequest().value(HttpServletRequest.class);
    log.info("请求地址：{}", request.getRequestURI());
    log.info("{}，消耗时间：{}ms", operation.getContent(), operation.getDuration());
    log.info("方法参数：" + JsonParsers.parseString(operateMeta.getMethodArgs()));
    log.info("方法返回值：" + JsonParsers.parseString(operateMeta.getMethodReturned()));
    if (operateMeta.getThrowable() != null) {
      log.info("异常信息：" + JsonParsers.parseString(operateMeta.getThrowable().getMessage()));
    }
    DONE.set(true);
  }
}

