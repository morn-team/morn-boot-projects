package site.morn.boot.response;

import site.morn.exception.ApplicationMessages;

import java.util.List;

/**
 * 转换响应解析器.
 *
 * @author yanhy
 * @since 2020-07-09 23:16:18
 */
public class RestResolver {

    public RestResolver (List<RestConverter> restConverters) {
        this.restConverters = restConverters;
    }

    private List<RestConverter> restConverters;

    public <T> Object convert (T t, Class<?> rClass) {
        for (RestConverter restConverter : restConverters) {
            if (restConverter.matching(t, rClass)) {
                return restConverter.conver(t);
            }
        }
        throw ApplicationMessages.translateException("没有可处理当前返回值的处理器");
    }

}
