package site.morn.boot.validator;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

/**
 * site.morn.boot.validator
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/16
 * @since 1.0
 */
@Aspect
@Component
@Log
public class ValidAspect {

//    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//    private final ExecutableValidator methodValidator = factory.getValidator().forExecutables();
//    private final Validator beanValidator = factory.getValidator();

    private final Validator validator;

    @Autowired
    public ValidAspect(Validator validator) {
        this.validator = validator;
    }

    //    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void pointcut() {

    }

//    @Pointcut("@annotation(javax.validation.Valid)")
//    public void valid() {
//
//    }

    @Before("pointcut()")
    public void validBefore(JoinPoint point) {
        log.info("ValidAspect.validBefore");
        Object[] args = point.getArgs();
    }

    @Around("pointcut()")
    public Object aroundValid(ProceedingJoinPoint point) {
        log.info("ValidAspect.aroundValid");
        try {
            return point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    @AfterReturning(pointcut = "pointcut()")
    public void requestReturning(JoinPoint point) {
        log.info("ValidAspect.requestReturning");
    }
}
