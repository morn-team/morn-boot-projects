package site.morn.boot.json;

import java.lang.reflect.Type;
import org.springframework.core.ParameterizedTypeReference;
import site.morn.core.CriteriaMap;

/**
 * JSON解析器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/24
 */
public interface JsonParser {

  /**
   * 解析为Map
   *
   * @param object 任意对象
   * @return 标准字典
   */
  CriteriaMap parseMap(Object object);

  /**
   * 解析为泛型对象
   *
   * @param object 任意对象
   * @param cls    类型
   * @param <T>    类泛型
   * @return Java对象
   */
  <T> T parseObject(Object object, Class<T> cls);

  /**
   * 解析为泛型对象
   *
   * @param object    任意对象
   * @param reference 类型引用
   * @param <T>       类泛型
   * @return Java对象
   */
  <T> T parseObject(Object object, ParameterizedTypeReference<T> reference);

  /**
   * 解析为泛型对象
   *
   * @param object 任意对象
   * @param type   解析类型
   * @param <T>    类泛型
   * @return Java对象
   */
  <T> T parseObject(Object object, Type type);

  /**
   * 解析为JSON字符串
   *
   * @param object 任意对象
   * @return JSON字符串
   */
  String parseString(Object object);
}
