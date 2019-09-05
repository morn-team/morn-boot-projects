package site.morn.boot.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.cache.annotation.Cacheable;
import site.morn.bean.AnnotationIdentify;
import site.morn.bean.BeanCache;
import site.morn.bean.FunctionHolder;
import site.morn.bean.IdentifiedBeanHolder;
import site.morn.constant.ApplicationConstant.Cache;
import site.morn.util.AnnotationIdentifyUtils;

/**
 * 默认标识的实例缓存
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/26
 */
public class SimpleBeanCache implements BeanCache {

  /**
   * 实例持有者
   */
  private final List<IdentifiedBeanHolder> holders = Collections
      .synchronizedList(new ArrayList<>());

  @Override
  public <T> void cache(IdentifiedBeanHolder<T> holder) {
    holders.add(holder);
  }

  @Cacheable(value = Cache.BEAN_DEFAULT, key = "#limitIdentify.toString()")
  @Override
  public <T> List<T> beans(Class<T> suitType, AnnotationIdentify limitIdentify) {
    Stream<IdentifiedBeanHolder<T>> stream = beanHolderStream(suitType, limitIdentify);
    // 提取实例集合
    return stream.map(IdentifiedBeanHolder::getBean).collect(Collectors.toList());
  }

  @Cacheable(value = Cache.BEAN_HOLDER, key = "#limitIdentify.toString()")
  @Override
  public <T> List<IdentifiedBeanHolder<T>> beanHolders(Class<T> suitType,
      AnnotationIdentify limitIdentify) {
    return beanHolderStream(suitType, limitIdentify).collect(Collectors.toList());
  }

  @Override
  public List<FunctionHolder> functions(AnnotationIdentify beanIdentify,
      AnnotationIdentify functionIdentify) {
    return functionHolderStream(beanIdentify, functionIdentify).collect(Collectors.toList());
  }

  @Override
  public <T> List<FunctionHolder> functions(List<IdentifiedBeanHolder<T>> holders,
      AnnotationIdentify functionIdentify) {
    return functionHolderStream(holders.stream(), functionIdentify).collect(Collectors.toList());
  }

  /**
   * 获取实例持有流
   *
   * @param limitType 限制类型
   * @param limitIdentify 限制标识
   * @param <T> 实例类型
   * @return 实例持有流
   */
  @SuppressWarnings("unchecked")
  private <T> Stream<IdentifiedBeanHolder<T>> beanHolderStream(Class<T> limitType,
      AnnotationIdentify limitIdentify) {
    return holders.stream().filter(
        holder -> AnnotationIdentifyUtils.isInstance(holder.getBean().getClass(), limitType))
        .filter(holder -> AnnotationIdentifyUtils.isSuitable(holder, limitIdentify))
        .map(identifiedBeanHolder -> (IdentifiedBeanHolder<T>) identifiedBeanHolder);
  }

  /**
   * 获取函数持有流
   *
   * @param beanIdentify 实例标识
   * @param functionIdentify 函数标识
   * @return 函数持有流
   */
  private Stream<FunctionHolder> functionHolderStream(AnnotationIdentify beanIdentify,
      AnnotationIdentify functionIdentify) {
    return functionHolderStream(beanHolderStream(null, beanIdentify), functionIdentify);
  }

  /**
   * 获取函数持有流
   *
   * @param stream 实例持有者流
   * @param functionIdentify 函数标识
   * @return 函数持有流
   */
  private <T> Stream<FunctionHolder> functionHolderStream(Stream<IdentifiedBeanHolder<T>> stream,
      AnnotationIdentify functionIdentify) {
    return stream.flatMap(holder -> holder.getFunctionHolders().stream())
        .filter(holder -> AnnotationIdentifyUtils.isSuitable(holder, functionIdentify));
  }
}
