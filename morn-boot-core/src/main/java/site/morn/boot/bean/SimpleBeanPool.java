package site.morn.boot.bean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import site.morn.bean.AnnotationFeature;
import site.morn.bean.BeanCache;
import site.morn.bean.BeanHolder;
import site.morn.bean.BeanPool;
import site.morn.util.AnnotationFeatureUtils;

/**
 * 默认实例池
 * <p>
 * 从{@link BeanCache}获取特定实例对象，提供简洁的检索API
 *
 * @author timely-rain
 * @since 1.2.2, 2021/9/27
 */
@RequiredArgsConstructor
public class SimpleBeanPool implements BeanPool {

  /**
   * 实例缓存
   */
  private final BeanCache beanCache;

  @Override
  public <T> List<T> beans(Class<T> type, AnnotationFeature feature) {
    Stream<BeanHolder<T>> stream = beanHolderStream(type, feature);
    // 提取实例集合
    return stream.map(BeanHolder::getBean).collect(Collectors.toList());
  }

  @Override
  public <T> List<BeanHolder<T>> beanHolders(Class<T> type, AnnotationFeature feature) {
    return beanHolderStream(type, feature).collect(Collectors.toList());
  }

  /**
   * 获取实例持有流
   *
   * @param type    实例类
   * @param feature 限制特征
   * @param <T>     实例类型
   * @return 实例持有流
   */
  @Override
  public <T> Stream<BeanHolder<T>> beanHolderStream(Class<T> type, AnnotationFeature feature) {
    List<BeanHolder<T>> beanHolders = Optional.ofNullable(type)
        .map(beanCache::getHolders).orElseGet(beanCache::getAllHolders);
    return beanHolders.stream()
        .filter(holder -> AnnotationFeatureUtils.isSuitable(holder, feature));
  }
}
