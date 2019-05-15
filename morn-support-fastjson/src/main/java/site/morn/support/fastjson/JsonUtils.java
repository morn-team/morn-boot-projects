package site.morn.support.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.experimental.UtilityClass;

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
   * X to JavaObject
   *
   * @param object 任意对象
   * @param cls 类型
   * @param <T> 类泛型
   * @return Java对象
   */
  public static <T> T toObject(Object object, Class<T> cls) {
    String jsonString = JSONObject.toJSONString(object);
    return JSONObject.parseObject(jsonString, cls);
  }

  /**
   * X to String
   *
   * @param object 任意对象
   * @return JSON字符串
   */
  public static String toString(Object object) {
    return JSON.toJSONString(object);
  }
}
