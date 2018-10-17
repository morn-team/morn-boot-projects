package site.morn.boot.exception;


import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionInterpreterCache;
import site.morn.exception.SimpleAnnotationExceptionInterpreterHolder;

/**
 * 异常解释器缓存测试
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/23
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleExceptionInterpreterCacheTest {

  @Autowired
  private ExceptionInterpreterCache exceptionInterpreterCache;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void find() {
    List<ExceptionInterpreter> resolversFirst = exceptionInterpreterCache.find("test");
    List<ExceptionInterpreter> resolversSecond = exceptionInterpreterCache.find("test");
  }

  @Test
  public void putResolvers() {
  }

  @Test
  public void putResolver() {
    SimpleAnnotationExceptionInterpreterHolder resolver = new SimpleAnnotationExceptionInterpreterHolder();
    resolver.setTags(new String[]{"test"});
    exceptionInterpreterCache.put(resolver);
  }
}
