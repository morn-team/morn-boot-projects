package site.morn.data.persistent;

/**
 * 数据更新校验
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
 */
@FunctionalInterface
public interface UpdateValidator<T> extends PersistValidator<T> {

}
