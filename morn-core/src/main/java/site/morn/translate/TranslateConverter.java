package site.morn.translate;


import site.morn.core.BeanConverter;

/**
 * 翻译转换器
 *
 * @param <T> 国际化类型
 * @author timely-rain
 * @since 1.0.0, 2018/12/9
 */
public interface TranslateConverter<T> extends BeanConverter<Transfer, T> {

}
