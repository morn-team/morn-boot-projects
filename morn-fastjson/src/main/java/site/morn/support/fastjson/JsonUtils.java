package site.morn.support.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.experimental.UtilityClass;
import site.morn.core.CriteriaMap;

/**
 * JSON转换工具
 *
 * @author TianGanLin
 */
@UtilityClass
public class JsonUtils {

  /**
   * X to JSONObject
   *
   * @param object 任意对象
   * @return JSON对象
   */
  public static JSONObject toJSONObject(Object object) {
    String jsonString = JSONObject.toJSONString(object);
    return JSONObject.parseObject(jsonString);
  }

  /**
   * X to CriteriaMap
   *
   * @param object 任意对象
   * @return 标准字典
   */
  public static CriteriaMap toMap(Object object) {
    String jsonString = JSONObject.toJSONString(object);
    JSONObject jsonObject = JSONObject.parseObject(jsonString);
    return new CriteriaMap(jsonObject);
  }

  /**
   * X to JavaObject
   *
   * @param object 任意对象
   * @param cls 类型
   * @param <T> 类泛型
   * @return Java对象
   */
  public static <T> T toObject(Object object, Class<T> cls) {
    String jsonString = toString(object);
    return JSONObject.parseObject(jsonString, cls);
  }

  /**
   * X to JavaObject
   *
   * @param object 任意对象
   * @param reference 类型引用
   * @param <T> 类泛型
   * @return Java对象
   */
  public static <T> T toObject(Object object, TypeReference<T> reference) {
    String jsonString = toString(object);
    return JSONObject.parseObject(jsonString, reference);
  }

  /**
   * X to String
   *
   * @param object 任意对象
   * @return JSON字符串
   */
  public static String toString(Object object) {
    return object instanceof String ? String.valueOf(object) : JSONObject.toJSONString(object);
  }
}
