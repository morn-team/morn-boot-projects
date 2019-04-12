package site.morn.boot.jpa;

import java.util.Map;
import java.util.Optional;
import site.morn.util.OptionalCollection;

/**
 * JPA查询参数实现
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/14
 */
public class JpaParameterSupport<M> implements JpaParameter<M> {

  @Override
  public JpaParameter model(M model) {
    return this;
  }

  @Override
  public JpaParameter attach(Map<String, Object> attach) {
    return this;
  }

  @Override
  public JpaParameter withNamePair(String pathName, String model) {
    return this;
  }

  @Override
  public <V> JpaParameter withValuePair(String pathName, V value) {
    return this;
  }

  @Override
  public <V> Optional<V> getOptional(String name) {
    return Optional.empty();
  }

  @Override
  public <V> OptionalCollection<V> getCollectionOptional(String name) {
    return null;
  }
}
