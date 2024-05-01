package moyeora.myapp.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerExecutionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExecutionAspect.class);

    @Pointcut("execution(* moyeora.myapp.controller.*.*(..))")
    public void controllerMethod() {
    }

    @Before("controllerMethod()")
    public void logControllerMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("메소드 ").append(methodName).append("파라미터: ");
        for (Object arg : args) {
            logMessage.append(arg).append(", ");
        }

        logger.info(logMessage.toString());
    }
}
