package site.morn.boot.exception;

import org.springframework.cache.annotation.Cacheable;
import site.timely.exception.AnnotationExceptionResolver;
import site.timely.exception.ExceptionResolver;
import site.timely.exception.ExceptionResolverCache;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * 默认异常处理器的缓存器
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/21
 * @since 1.0
 */
public class SimpleExceptionResolverCache implements ExceptionResolverCache {

    /**
     * 仓库
     */
    private List<AnnotationExceptionResolver> store;

    public SimpleExceptionResolverCache() {
        store = new Vector<>();
    }

    @Cacheable(value = DEFAULT_CACHE, key = "'TAG.'+T(org.springframework.util.StringUtils).arrayToCommaDelimitedString(#tag)")
    @Override
    public List<ExceptionResolver> find(String tag) {
        return store.stream().filter(resolver -> Arrays.binarySearch(resolver.getTags(), tag) >= 0).collect(Collectors.toList());
    }

    @Cacheable(value = DEFAULT_CACHE, key = "'CLASS.'+T(org.springframework.util.StringUtils).arrayToCommaDelimitedString(#targets)")
    @Override
    public List<ExceptionResolver> find(Class<?>... targets) {
        return store.stream().filter(resolver -> {
            for (Class<?> resolverTarget : resolver.getTargets()) {
                for (Class<?> target : targets) {
                    if (target.isAssignableFrom(resolverTarget))
                        return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public void put(List<AnnotationExceptionResolver> resolvers) {
        store.addAll(resolvers);
    }

    @Override
    public void put(AnnotationExceptionResolver resolver) {
        store.add(resolver);
    }
}
