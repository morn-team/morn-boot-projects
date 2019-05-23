package site.morn.boot.log;

import java.util.Date;
import site.morn.bean.annotation.Tag;
import site.morn.log.OperateMeta;
import site.morn.log.Operation;
import site.morn.log.OperationConverter;
import site.morn.translate.Translator;

/**
 * 默认操作日志转换器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Tag
public class DefaultOperationConverter implements OperationConverter {

  /**
   * 翻译器
   */
  private final Translator translator;

  public DefaultOperationConverter(Translator translator) {
    this.translator = translator;
  }

  @Override
  public Operation convert(OperateMeta operateMeta) {
    // 拼接国际化编码
    String messageCode = "log." + operateMeta.getModule() + "." + operateMeta.getName();
    // 生成操作内容
    String content = translator.translate(messageCode, operateMeta.getActionArgs());
    // 操作时间
    Date date = new Date();
    // 构建操作日志实例
    Operation operation = new Operation();
    operation.setSuccess(operateMeta.isSuccess());
    operation.setModule(operateMeta.getModule());
    operation.setContent(content);
    operation.setDate(date);
    return operation;
  }
}
