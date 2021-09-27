package site.morn.boot.json.support;

import static site.morn.boot.json.constant.JsonParserConstants.JACKSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import site.morn.bean.annotation.Tag;
import site.morn.boot.json.JsonParser;
import site.morn.constant.ApplicationConstants.Errors;
import site.morn.core.CriteriaMap;
import site.morn.exception.ApplicationMessages;

/**
 * FastJson解析器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/25
 */
@Slf4j
@Tag(JACKSON)
public class JacksonParser implements JsonParser {

  private final ObjectMapper objectMapper;

  public JacksonParser(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public CriteriaMap parseMap(Object object) {
    String string = parseString(object);
    try {
      return objectMapper.readValue(string, CriteriaMap.class);
    } catch (IOException e) {
      throw ApplicationMessages
          .translateException(Errors.JSON_DESERIALIZE_FAILURE, object.toString());
    }
  }

  @Override
  public <T> T parseObject(Object object, Class<T> cls) {
    String string = parseString(object);
    try {
      return objectMapper.readValue(string, cls);
    } catch (IOException e) {
      throw ApplicationMessages
          .translateException(Errors.JSON_DESERIALIZE_FAILURE, object.toString());
    }
  }

  @Override
  public <T> T parseObject(Object object, ParameterizedTypeReference<T> reference) {
    Type type = reference.getType();
    return parseObject(object, type);
  }

  @Override
  public <T> T parseObject(Object object, Type type) {
    try {
      String string = parseString(object);
      JavaType javaType = objectMapper.getTypeFactory().constructType(type);
      return objectMapper.readValue(string, javaType);
    } catch (IOException e) {
      throw ApplicationMessages
          .translateException(Errors.JSON_DESERIALIZE_FAILURE, object.toString());
    }
  }

  @Override
  public String parseString(Object object) {
    if (object instanceof String) {
      return object.toString();
    }
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw ApplicationMessages
          .translateException(Errors.JSON_SERIALIZE_FAILURE, object.toString());
    }
  }
}
