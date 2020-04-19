package site.morn.data.persistent;

/**
 * 数据删除校验
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/19
 */
@FunctionalInterface
public interface DeleteValidator<T> extends PersistValidator<T> {

}
