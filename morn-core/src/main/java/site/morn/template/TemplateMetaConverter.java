package site.morn.template;

import site.morn.core.BeanConverter;

/**
 * 模板元数据转换器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/12
 */
@FunctionalInterface
public interface TemplateMetaConverter<T> extends BeanConverter<TemplateMeta, T> {

}
