package site.morn.boot.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import site.morn.bean.AnnotationIdentify;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.bean.IdentifiedBeanHolder;
import site.morn.util.ArrayUtils;

/**
 * 默认标识的实例缓存
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/26
 */
public class SimpleIdentifiedBeanCache implements IdentifiedBeanCache {

  /**
   * 实例持有者
   */
  private List<IdentifiedBeanHolder> holders = Collections.synchronizedList(new ArrayList<>());

  @Override
  public <T> void register(IdentifiedBeanHolder<T> holder) {
    holders.add(holder);
  }

  @Cacheable(value = DEFAULT_CACHE, key = "#identify.toString()")
  @SuppressWarnings("unchecked")
  @Override
  public <T> List<T> beans(Class<T> type, AnnotationIdentify identify) {
    Stream<IdentifiedBeanHolder> stream = holders.stream();
    // 按类型过滤beanHolder
    stream = stream.filter(
        identifiedBeanHolder -> type.isAssignableFrom(identifiedBeanHolder.getBean().getClass()));
    // 将剩余beanHolder转换为IdentifiedBeanHolder<T>
    Stream<IdentifiedBeanHolder<T>> holderStream = stream
        .map(identifiedBeanHolder -> (IdentifiedBeanHolder<T>) identifiedBeanHolder);
    // 按名称过滤实例
    if (!StringUtils.isEmpty(identify.getName())) {
      holderStream = holderStream.filter(holder -> Objects
          .equals(identify.getName(), holder.getIdentify().getName()));
    }
    // 按标签过滤实例
    if (Objects.nonNull(identify.getTags())) {
      holderStream = holderStream.filter(holder -> ArrayUtils
          .anySearch(identify.getTags(), holder.getIdentify().getTags()));
    }
    // 按目标过滤实例
    if (Objects.nonNull(identify.getTarget())) {
      holderStream = holderStream
          .filter(holder -> Objects
              .equals(identify.getTarget(), holder.getIdentify().getTarget()));
    }
    // 提取实例集合
    Stream<T> beans = holderStream.map(IdentifiedBeanHolder::getBean);
    return beans.collect(Collectors.toList());
  }
}
