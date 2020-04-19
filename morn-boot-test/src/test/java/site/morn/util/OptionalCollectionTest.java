package site.morn.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 可选集合单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/17
 */
@Slf4j
@RunWith(JUnit4.class)
public class OptionalCollectionTest {

  private final List<Integer> numbers = Stream.of(1, 2, 3).collect(Collectors.toList());

  @Test
  public void ifPresent() {
    OptionalCollection<Object> nullable = OptionalCollection.ofNullable(null);
    nullable.ifPresent(objects -> log.info("is present:{}", objects.size()));
    Assert.assertEquals(false, nullable.isPresent());
  }

  @Test
  public void filter() {
    Collection<Integer> list1 = OptionalCollection.of(numbers)
        .filter(integers -> integers.size() == 3).orElse(Collections.emptyList());
    Assert.assertEquals(3, list1.size());
  }

  @Test
  public void map() {
    Collection<Object> objects = OptionalCollection.ofNullable(numbers)
        .map(integers -> Collections.emptyList()).orElseGet(Collections::emptyList);
    Assert.assertEquals(0, objects.size());
  }

  @Test
  public void flatMap() {
    try {
      OptionalCollection.ofNullable(numbers).flatMap(integers -> OptionalCollection.empty())
          .orElseThrow(IllegalArgumentException::new);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage(), e);
    }
  }
}