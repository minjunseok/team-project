package moyeora.myapp.aspect;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Aspect
public class ServiceExecutionAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceExecutionAspect.class);

    @Pointcut("execution(* moyeora.myapp.service.*.*(..))")
    public void serviceMethod() {
    }

    @Before("serviceMethod()")
    public void logServiceMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("메소드 ").append(methodName).append("파라미터: ");
        for (Object arg : args) {
            logMessage.append(arg).append(", ");
        }

        logger.info(logMessage.toString());
    }

    @AfterReturning(pointcut = "serviceMethod()", returning = "result")
    public void serviceMethodExecution(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("##############=>" + joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        Map<String, String> map = new HashMap<>();
        map.put("메소드", methodName);
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("메소드 ").append(methodName).append(" 매개변수: ");
        for (Object arg : args) {
            if (arg instanceof org.springframework.ui.Model) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(arg);
                    logMessage.append(json).append(", ");
                } catch (Exception e) {

                }
            } else {
                logMessage.append(arg).append(", ");
            }
        }
        logMessage.append("리턴값 ").append(result);

        logger.info(logMessage.toString());
    }
}
