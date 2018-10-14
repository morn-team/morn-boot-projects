package site.morn.boot.exception;

import site.timely.exception.ExceptionResolver;
import site.timely.exception.ExceptionResolverCache;

import java.util.ArrayList;
import java.util.List;

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
    private List<ExceptionResolver> store = new ArrayList<>();

    //    @Cacheable(value = DEFAULT_CACHE, key = "'TAG.'+T(org.springframework.util.StringUtils).arrayToCommaDelimitedString(#tag)")
    @Override
    public List<ExceptionResolver> find(String tag) {
//        return store.stream().filter(resolver -> Arrays.binarySearch(resolver.getTags(), tag) >= 0).collect(Collectors.toList());
        return null;
    }

    //    @Cacheable(value = DEFAULT_CACHE, key = "'CLASS.'+T(org.springframework.util.StringUtils).arrayToCommaDelimitedString(#targets)")
    @Override
    public List<ExceptionResolver> find(Class<?>... targets) {
//        return store.stream().filter(resolver -> {
//            for (Class<?> resolverTarget : resolver.getTargets()) {
//                for (Class<?> target : targets) {
//                    if (target.isAssignableFrom(resolverTarget))
//                        return true;
//                }
//            }
//            return false;
//        }).collect(Collectors.toList());
        return null;
    }

    @Override
    public void put(ExceptionResolver resolver) {
        store.add(resolver);
    }
}
