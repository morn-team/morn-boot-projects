package site.morn.util;

import java.util.Collection;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

/**
 * 备用集合工具类
 *
 * <p>生产环境请使用{@link CollectionUtils}</p>
 *
 * @author timely-rain
 * @since 1.2.2, 2021/7/20
 */
@UtilityClass
public class SpareCollectionUtils {

  /**
   * 将集合用null填充
   *
   * <p>确保集合在初始化前已设置初始容量：initialCapacity</p>
   *
   * @param collection 集合
   * @param size       填充数量
   */
  public void initCollection(Collection<?> collection, int size) {
    for (int i = 0; i < size; i++) {
      collection.add(null);
    }
  }
}
