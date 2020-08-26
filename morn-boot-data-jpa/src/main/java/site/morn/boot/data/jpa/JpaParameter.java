package site.morn.boot.data.jpa;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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

  <V, R> R mapOptional(String name, Function<V, R> mapper);

  <V, R> R mapOptional(Optional<V> optional, Function<V, R> mapper);
}
