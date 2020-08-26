package site.morn.boot.data.util;

import java.lang.reflect.Field;
import java.util.Objects;
import javax.persistence.Id;
import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import site.morn.util.GenericUtils;

@UtilityClass
public class EntityUtils {

  /**
   * 获取主键值
   *
   * @param entity 实体对象
   * @return 主键值
   */
  public <T> T getId(Object entity) {
    Field idField = EntityUtils.getIdField(entity);
    Assert.notNull(idField, "无法获取实体主键：" + entity.getClass());
    return getFieldValue(entity, idField);
  }

  /**
   * 获取主键属性
   *
   * @param entity 实体对象
   * @return 主键属性
   */
  public Field getIdField(Object entity) {
    Class<?> entityClass = entity.getClass();
    while (!Objects.equals(Object.class, entityClass)) {
      Field[] fields = entityClass.getDeclaredFields();
      for (Field field : fields) {
        Id annotation = AnnotationUtils.findAnnotation(field, Id.class);
        if (Objects.nonNull(annotation)) {
          return field;
        }
      }
      entityClass = entityClass.getSuperclass();
    }
    return null;
  }

  /**
   * 获取属性值
   *
   * @param o     对象
   * @param field 属性
   * @param <T>   值类型
   * @return 属性值
   */
  public <T> T getFieldValue(Object o, Field field) {
    ReflectionUtils.makeAccessible(field);
    return GenericUtils.castFrom(ReflectionUtils.getField(field, o));
  }
}
