package site.morn.boot.jpa;

import java.util.Map;
import java.util.Optional;
import site.morn.util.OptionalCollection;

/**
 * JPA查询参数
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public interface JpaParameter<M> {

  JpaParameter model(M model);

  JpaParameter attach(Map<String, Object> attach);

  JpaParameter withNamePair(String pathName, String model);

  <V> JpaParameter withValuePair(String pathName, V value);

  <V> Optional<V> getOptional(String name);

  <V> OptionalCollection<V> getCollectionOptional(String name);
}
