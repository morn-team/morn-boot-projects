package site.morn.boot.exception;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.timely.exception.ExceptionResolver;
import site.timely.exception.ExceptionResolverCache;
import site.timely.exception.SimpleAnnotationExceptionResolver;

import java.util.List;

/**
 * 异常解释器缓存测试
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/23
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleExceptionResolverCacheTest {

    @Autowired
    private ExceptionResolverCache simpleExceptionResolverCache;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void find() {
        List<ExceptionResolver> resolversFirst = simpleExceptionResolverCache.find("test");
        List<ExceptionResolver> resolversSecond = simpleExceptionResolverCache.find("test");
    }

    @Test
    public void putResolvers() {
    }

    @Test
    public void putResolver() {
        SimpleAnnotationExceptionResolver resolver = new SimpleAnnotationExceptionResolver();
        resolver.setTags(new String[]{"test"});
        simpleExceptionResolverCache.put(resolver);
    }
}
