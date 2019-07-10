package site.morn.validate.persistent;

import site.morn.core.BeanProcessor;

/**
 * 持久化处理者
 *
 * @author timely-rain
 * @since 2.1.0, 2019/7/10
 */
@FunctionalInterface
public interface PersistProcessor<T> extends BeanProcessor<T> {

}
