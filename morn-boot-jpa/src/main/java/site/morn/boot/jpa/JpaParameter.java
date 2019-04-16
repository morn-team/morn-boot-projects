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

  JpaParameter<M> model(M model);

  JpaParameter<M> attach(Map<String, Object> attach);

  JpaParameter<M> withNamePair(String pathName, String model);

  <V> JpaParameter<M> withValuePair(String pathName, V value);

  <V> Optional<V> getOptional(String name);

  Optional<String> getStringOptional(String name);

  <V> OptionalCollection<V> getCollectionOptional(String name);
}
