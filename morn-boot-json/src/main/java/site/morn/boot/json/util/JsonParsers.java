package site.morn.boot.json.util;

import java.lang.reflect.Type;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.Assert;
import site.morn.bean.support.BeanCaches;
import site.morn.boot.json.JsonParser;
import site.morn.boot.json.constant.JsonParserConstants;
import site.morn.core.CriteriaMap;

/**
 * JSON解析工具栏
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/25
 */
public class JsonParsers {

  private JsonParsers() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * 获取默认解析器
   *
   * @return JSON解析器
   */
  public static JsonParser getParser() {
    JsonParser parser = BeanCaches.bean(JsonParser.class);
    Assert.notNull(parser, "无法获取默认JSON解析器");
    return parser;
  }

  /**
   * 获取解析器
   *
   * @param type 解析器类型
   * @return JSON解析器
   * @see JsonParserConstants 解析器类型
   */
  public static JsonParser getParser(String type) {
    JsonParser parser = BeanCaches.tagBean(JsonParser.class, type);
    Assert.notNull(parser, "无法获取JSON解析器：" + type);
    return parser;
  }


  /**
   * 解析为Map
   *
   * @param object 任意对象
   * @return 标准字典
   */
  public static CriteriaMap parseMap(Object object) {
    return getParser().parseMap(object);
  }

  /**
   * 解析为泛型对象
   *
   * @param object 任意对象
   * @param cls    类型
   * @param <T>    类泛型
   * @return Java对象
   */
  public static <T> T parseObject(Object object, Class<T> cls) {
    return getParser().parseObject(object, cls);
  }

  /**
   * 解析为泛型对象
   *
   * @param object    任意对象
   * @param reference 类型引用
   * @param <T>       类泛型
   * @return Java对象
   */
  public static <T> T parseObject(Object object, ParameterizedTypeReference<T> reference) {
    return getParser().parseObject(object, reference);
  }

  /**
   * 解析为泛型对象
   *
   * @param object 任意对象
   * @param type   解析类型
   * @param <T>    类泛型
   * @return Java对象
   */
  public static <T> T parseObject(Object object, Type type) {
    return getParser().parseObject(object, type);
  }

  /**
   * 解析为JSON字符串
   *
   * @param object 任意对象
   * @return JSON字符串
   */
  public static String parseString(Object object) {
    return getParser().parseString(object);
  }
}
