package site.morn.boot.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import site.morn.bean.BeanPool;
import site.morn.exception.ApplicationMessages;
import site.morn.rest.RestMessage;
import site.morn.rest.RestMessageConverter;
import site.morn.rest.constant.RestMessageLevel;
import site.morn.rest.support.SimpleRestMessage;
import site.morn.translate.Transfer;
import site.morn.translate.Transfer.TransferBuilder;
import site.morn.translate.Translator;
import site.morn.translate.Translators;
import site.morn.util.BeanFunctionUtils;

/**
 * REST构建器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/7/25
 */
public class RestBuilder {

  /**
   * 标识实例缓存
   */
  private static BeanPool beanPool;

  /**
   * 翻译器
   */
  private static Translator translator;

  /**
   * REST配置项
   */
  private static RestProperties restProperties;

  /**
   * 消息体
   */
  private RestMessage restMessage;

  /**
   * 翻译载体构建器
   */
  private TransferBuilder transferBuilder;

  private RestBuilder() {
  }

  /**
   * 初始化
   *
   * @param beanPool       标识实例缓存
   * @param translator     翻译器
   * @param restProperties REST配置项
   */
  public static void initialize(BeanPool beanPool, Translator translator,
      RestProperties restProperties) {
    RestBuilder.beanPool = beanPool;
    RestBuilder.translator = translator;
    RestBuilder.restProperties = restProperties;
  }

  /**
   * 获取REST配置项
   *
   * @return REST配置项
   */
  public static RestProperties properties() {
    return restProperties;
  }

  /**
   * 获取REST构建器
   *
   * @return REST构建器
   */
  public static RestBuilder builder() {
    return new RestBuilder().restMessage(new SimpleRestMessage());
  }

  /**
   * 根据外来消息生成REST消息
   *
   * @param foreign 外来消息
   * @param <T>     外来消息类型
   * @return REST消息
   * @see RestMessageConverter REST消息转换器
   */
  @SuppressWarnings("unchecked")
  public static <T> RestMessage from(T foreign) {
    RestMessageConverter<T> restMessageConverter = beanPool
        .targetBean(RestMessageConverter.class, foreign.getClass());
    if (Objects.isNull(restMessageConverter)) {
      return null;
    }
    return restMessageConverter.revert(foreign);
  }

  /**
   * 根据REST消息生成外来消息
   *
   * @param restMessage REST消息
   * @param foreign 外来消息
   * @param <T> 外来消息类型
   * @return 外来消息
   * @see RestMessageConverter REST消息转换器
   */
  public static <T> T to(RestMessage restMessage, Class<T> foreign) {
    return BeanFunctionUtils.convert(RestMessageConverter.class, restMessage, foreign);
  }

  /**
   * 基于RestProperties配置翻译消息
   *
   * @param transfer 翻译载体
   * @return 消息内容
   */
  public static String translateMessage(Transfer transfer) {
    return translateMessage(transfer.getCode(), transfer.getArgs());
  }

  /**
   * 基于RestProperties配置翻译消息
   *
   * @param code 消息编码
   * @param args 消息参数
   * @return 消息内容
   */
  public static String translateMessage(String code, Object... args) {
    String messageCode = Translators.formatCode(restProperties.getPrefix(), code,
        restProperties.getMessageSuffix());
    return translator.translate(messageCode, args);
  }

  /**
   * 根据当前REST消息，生成外来消息
   *
   * @param foreign 外来消息类
   * @param <T> 外来消息类型
   * @return 外来消息
   */
  public <T> T to(Class<T> foreign) {
    return BeanFunctionUtils.convert(RestMessageConverter.class, build(), foreign);
  }

  /**
   * 翻译REST消息
   *
   * <p>如果显示的指定了消息内容{@link RestBuilder#message(String)}，则不会进行翻译。
   *
   * @return REST构建器
   */
  public RestBuilder translate() {
    Transfer transfer = transferBuilder().build();
    restMessage.setCode(transfer.getCode());
    if (Objects.isNull(restMessage.getMessage())) {
      String message = translateMessage(transfer);
      restMessage.setMessage(message);
    }
    return this;
  }

  /**
   * 构建REST消息
   *
   * <p>构建消息时，会进行翻译操作{@link Translator#translate(Transfer)}。
   * 如果显示的指定了消息内容{@link RestBuilder#message(String)}，则不会进行翻译。
   *
   * @return REST消息
   */
  public RestMessage build() {
    translate();
    return restMessage;
  }

  /**
   * 设置REST消息体
   *
   * @return REST构建器
   */
  public RestBuilder restMessage(RestMessage restMessage) {
    this.restMessage = restMessage;
    return this;
  }

  /**
   * 设置国际化载体
   *
   * @param code 国际化编码
   * @param args 国际化参数
   * @return REST构建器
   */
  public RestBuilder transfer(String code, Object... args) {
    transferBuilder().code(code).args(args);
    return this;
  }

  /**
   * 设置状态码
   *
   * @param value 状态码
   * @return REST构建器
   */
  public RestBuilder status(int value) {
    restMessage.setStatus(value);
    return this;
  }

  /**
   * 设置消息级别
   *
   * @param level 消息级别
   * @return REST构建器
   */
  public RestBuilder level(RestMessageLevel level) {
    restMessage.setLevel(level);
    return this;
  }

  /**
   * 设置状态码
   *
   * @param value 状态码
   * @return REST构建器
   */
  public RestBuilder code(String value) {
    transferBuilder().code(value);
    return this;
  }

  /**
   * 设置消息内容
   *
   * @param value 消息内容
   * @return REST构建器
   */
  public RestBuilder message(String value) {
    restMessage.setMessage(value);
    return this;
  }

  /**
   * 设置消息数据
   *
   * @param value 消息数据
   * @return REST构建器
   */
  public RestBuilder data(Object value) {
    restMessage.setData(value);
    return this;
  }

  /**
   * 设置消息数据
   *
   * @param key 参数名称
   * @param value 参数值
   * @return REST构建器
   */
  @SuppressWarnings("unchecked")
  public RestBuilder data(String key, Object value) {
    Object data = restMessage.getData();
    if (Objects.isNull(data)) {
      data = new HashMap<>();
      restMessage.setData(data);
    }
    if (data instanceof Map) {
      ((Map<String, Object>) data).put(key, value);
    } else {
      throw ApplicationMessages.buildException("rest.data-not-map", "REST数据不是Map类型",
          "请使用RestBuilder#data(Object value)");
    }
    return this;
  }

  /**
   * 获取翻译载体构建器
   *
   * @return 翻译载体构建器
   */
  private TransferBuilder transferBuilder() {
    if (Objects.isNull(transferBuilder)) {
      transferBuilder = Transfer.builder();
    }
    return transferBuilder;
  }
}
