package site.morn.boot.log;

import static site.morn.log.OperateModes.I18N;

import site.morn.boot.log.OperateProperties.I18n;
import site.morn.log.OperateMeta;
import site.morn.log.OperateMode;
import site.morn.log.Operation;
import site.morn.log.OperationConverter;
import site.morn.translate.Translator;

/**
 * 国际化操作日志转换器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@OperateMode(I18N)
public class I18nOperationConverter implements OperationConverter {

  /**
   * 操作日志配置项
   */
  private final OperateProperties properties;

  /**
   * 翻译器
   */
  private final Translator translator;

  public I18nOperationConverter(OperateProperties properties,
      Translator translator) {
    this.properties = properties;
    this.translator = translator;
  }

  @Override
  public Operation convert(OperateMeta operateMeta) {
    // 拼接国际化编码
    I18n i18n = properties.getI18n();
    String messageCode =
        i18n.getPrefix() + i18n.getDelimiter() + operateMeta.getGroupName() + i18n.getDelimiter()
            + operateMeta.getActionName();
    // 生成操作内容
    String content = translator.translate(messageCode, operateMeta.getActionArgs());
    // 构建操作实例
    Operation operation = new Operation();
    operation.setContent(content);
    return operation;
  }
}
