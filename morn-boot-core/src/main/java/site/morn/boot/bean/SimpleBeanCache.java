package site.morn.boot.bean;

import static site.morn.constant.ApplicationConstants.Caches.CACHE_MANAGER_NAME_SIMPLE;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import site.morn.bean.BeanCache;
import site.morn.bean.BeanHolder;
import site.morn.constant.ApplicationConstants.Caches;

/**
 * 默认特征实例缓存
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/26
 */
@RequiredArgsConstructor
@CacheConfig(cacheManager = CACHE_MANAGER_NAME_SIMPLE)
public class SimpleBeanCache implements BeanCache {

  /**
   * 实例持有者
   */
  private final Map<String, BeanHolder<?>> holderMap = new ConcurrentHashMap<>(16);

  /**
   * 实例工厂
   */
  private final ListableBeanFactory beanFactory;

  @Override
  @SuppressWarnings("all")
  @Cacheable(value = Caches.BEAN_HOLDER)
  public <T> List<BeanHolder<T>> getAllHolders() {
    return holderMap.values().stream().map(beanHolder -> (BeanHolder<T>) beanHolder)
        .collect(Collectors.toList());
  }

  @Override
  @SuppressWarnings("all")
  @Cacheable(value = Caches.BEAN_HOLDER, key = "#type")
  public <T> List<BeanHolder<T>> getHolders(Class<T> type) {
    String[] names = beanFactory.getBeanNamesForType(type);
    return Stream.of(names).map(holderMap::get).map(beanHolder -> (BeanHolder<T>) beanHolder)
        .collect(Collectors.toList());
  }

  @Override
  public <T> void put(String beanName, BeanHolder<T> holder) {
    holderMap.put(beanName, holder);
  }
}
