package ggudock.global.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* ggudock.domain..*(..))")
    public void all() {
    }

    @Pointcut("execution(* ggudock.domain..*Controller.*(..))")
    public void controller() {
    }

    @Pointcut("execution(* ggudock.domain..*Service.*(..))")
    public void service() {
    }

    @Around("all()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("log = {}", joinPoint.getSignature());
            log.info("timeMs = {}", timeMs);
        }
    }

    // Pointcut에 의해 필터링된 경로로 들어오는 경우 메서드 호출 전에 적용
    @Before("controller() || service()")
    public void beforeLogic(JoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Method method = getMethod(joinPoint);
        log.info("");
        log.info("[{}] {}", request.getMethod(), URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8));
        log.info("====================Request info====================");
        log.info("Method Name : {}", method.getName());

        // 파라미터 받아오기
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) log.info("no parameter");
        else {
            for (Object arg : args) {
                if(arg != null) {
                    log.info("parameter type = {}", arg.getClass().getSimpleName());
                    log.info("parameter value = {}", arg);
                }
            }
        }
    }

    // Poincut에 의해 필터링된 경로로 들어오는 경우 메서드 리턴 후에 적용
    @AfterReturning("controller() || service()")
    public void afterLogic(JoinPoint joinPoint) {
        // 메서드 정보 받아오기
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("====================Response info====================");
        log.info("Method Name : {}", method.getName());

        Object[] args = joinPoint.getArgs();
        if (args.length == 0) log.info("no parameter");
        else {
            for (Object arg : args) {
                if(arg != null) {
                    log.info("parameter type = {}", arg.getClass().getSimpleName());
                    log.info("parameter value = {}", arg);
                }
            }
        }
    }

    // JoinPoint로 메서드 정보 가져오기
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}