package site.morn.boot.response;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

/**
 * 转换响应结果切面.
 *
 * @author yanhy
 * @since 2020-07-10 0:0:1
 */
@Aspect
@Slf4j
public class RestAspect {

    private RestResolver restResolver;

    /**
     * 转换返回结果切点
     */
    @Pointcut("@annotation(site.morn.boot.response.RestResponse)")
    public void pointcut() {
    }

    /**
     * 执行后返回.
     *
     * @param point 切点信息
     * @param ret 切入方法的返回值
     * @return 最终结果
     */
    @AfterReturning(pointcut = "pointcut()", returning = "ret")
    public Object afterReturning(ProceedingJoinPoint point, Object ret) {
        // 获取操作行为注解
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        RestResponse restResponse = AnnotationUtils.findAnnotation(methodSignature.getMethod(), RestResponse.class);
        Assert.notNull(restResponse, "没有指定转换结果类型");
        return restResolver.convert(ret, restResponse.value());
    }

}
