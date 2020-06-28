package site.morn.support.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import java.io.Serializable;
import java.lang.reflect.Type;
import lombok.experimental.UtilityClass;
import site.morn.core.CriteriaMap;

/**
 * JSON转换工具
 *
 * @author timely-rain
 * @since 1.0.0, 2019/9/5
 * @deprecated 为了和FastJson解耦，相关功能在morn-boot-json中重构
 */
@Deprecated
@UtilityClass
public class JsonUtils {

  /**
   * 克隆
   *
   * @param object 任意对象
   * @param cls 对象类
   * @param <T> 对象类型
   * @return 克隆对象
   */
  public static <T extends Serializable> T clone(T object, Class<T> cls) {
    String s = toString(object);
    return toObject(s, cls);
  }

  /**
   * 克隆
   *
   * @param object 任意对象
   * @param reference 类型引用
   * @param <T> 对象类型
   * @param <R> 实际类型
   * @return 克隆对象
   */
  public static <T extends Serializable, R> R clone(T object, TypeReference<R> reference) {
    return toObject(object, reference);
  }

  /**
   * X to JSONObject
   *
   * @param object 任意对象
   * @return JSON对象
   */
  public static JSONObject toJSONObject(Object object) {
    String jsonString = JSON.toJSONString(object);
    return JSON.parseObject(jsonString);
  }

  /**
   * X to CriteriaMap
   *
   * @param object 任意对象
   * @return 标准字典
   */
  public static CriteriaMap toMap(Object object) {
    String jsonString = JSON.toJSONString(object);
    JSONObject jsonObject = JSON.parseObject(jsonString);
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
    return JSON.parseObject(jsonString, cls);
  }

  /**
   * X to JavaObject
   *
   * @param object 任意对象
   * @param type 类型引用
   * @param <T> 类泛型
   * @return Java对象
   */
  public static <T> T toObject(Object object, Type type) {
    String jsonString = toString(object);
    return JSON.parseObject(jsonString, type);
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
    return JSON.parseObject(jsonString, reference);
  }

  /**
   * X to String
   *
   * @param object 任意对象
   * @return JSON字符串
   */
  public static String toString(Object object) {
    return object instanceof String ? String.valueOf(object) : JSON.toJSONString(object);
  }
}
