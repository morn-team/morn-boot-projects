package site.morn.boot.response;

/**
 * 转换响应结果接口.
 *
 * @author yanhy
 * @since 2020-07-09 23:56:33
 */
public interface RestConverter {

    /**
     * 转换方法,将T类型对象转换为R.
     *
     * @param t 待转换对象
     * @return 转换后对象
     */
    <T, R> R conver(T t);

    /**
     * 匹配是否可转换对象.
     *
     * @param t 待转换对象
     * @param rClazz 返回结果类型
     * @return 匹配结果
     */
    <T> boolean matching(T t, Class<?> rClazz);

}
