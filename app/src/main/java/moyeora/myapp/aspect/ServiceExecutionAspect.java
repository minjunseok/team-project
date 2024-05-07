package moyeora.myapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ServiceExecutionAspect {
    @Pointcut("execution(* moyeora.myapp.dao..*.*(..))")
    public void daoMethod() {
    }

    @AfterReturning("daoMethod()")
    public void logDaoBefore(JoinPoint joinPoint) {
        Map<String, Object> map = new HashMap<>();
        String className = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();
    }
}
