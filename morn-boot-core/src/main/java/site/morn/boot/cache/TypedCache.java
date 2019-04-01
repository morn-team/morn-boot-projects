package site.morn.boot.cache;

/**
 * 分类缓存
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/29
 */
public interface TypedCache {

  <T> T get(String type, String key);

  void put(String type, String key, Object value);
}
