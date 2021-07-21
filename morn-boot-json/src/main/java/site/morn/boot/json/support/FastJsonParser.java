package site.morn.boot.json.support;

import static site.morn.boot.json.constant.JsonParserConstants.FAST_JSON;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.Type;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import site.morn.bean.annotation.Tag;
import site.morn.boot.json.JsonParser;
import site.morn.core.CriteriaMap;

/**
 * FastJson解析器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/24
 */
@Tag(FAST_JSON)
public class FastJsonParser implements JsonParser {

  @Override
  public CriteriaMap parseMap(Object object) {
    String text = parseString(object);
    JSONObject jsonObject = JSON.parseObject(text);
    return new CriteriaMap(jsonObject);
  }

  @Override
  public <T> T parseObject(Object object, Class<T> cls) {
    String text = parseString(object);
    return JSON.parseObject(text, cls);
  }

  @Override
  public <T> T parseObject(Object object, ParameterizedTypeReference<T> reference) {
    ResolvableType resolvableType = ResolvableType.forType(reference);
    Type type = resolvableType.getType();
    return parseObject(object, type);
  }

  @Override
  public <T> T parseObject(Object object, Type type) {
    String text = parseString(object);
    return JSON.parseObject(text, type);
  }

  @Override
  public String parseString(Object object) {
    if (object instanceof String) {
      return object.toString();
    }
    return JSON.toJSONString(object);
  }
}
