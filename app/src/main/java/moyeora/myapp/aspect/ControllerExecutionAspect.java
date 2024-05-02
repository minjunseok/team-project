package moyeora.myapp.aspect;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Aspect
public class ControllerExecutionAspect {

    private static final Logger logger = LogManager.getLogger(ControllerExecutionAspect.class);

    @Pointcut("execution(* moyeora.myapp.controller.*.*(..))")
    public void controllerMethod() {
    }

    @AfterReturning(pointcut = "controllerMethod()", returning = "result")
    public void logControllerMethodCall(JoinPoint joinPoint, Object result) throws Exception {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getClass().getName();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> map = new HashMap<>();
        ObjectMapper obj = new ObjectMapper();
        obj.getFactory().configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        map.put("class", className);
        map.put("method", methodName);
        List<Object> arr = new ArrayList<>();
        for (Object arg : args) {
            arr.add(objectConvert(arg));
        }
        map.put("args", arr);
        map.put("result", objectConvert(result));
        System.out.println("============>" + obj.writeValueAsString(map));
        logger.info(map);
    }

    public Object objectConvert(Object arg) throws Exception {
        if (arg instanceof Integer) {
            return arg;
        }
        if (arg instanceof String) {
            return arg;
        }
        if (arg instanceof List<?>) {
            List<Object> arr = new ArrayList<>();
            for (Object obj : (List) arg) {
                arr.add(objectConvert(obj));
            }
        }
        ObjectMapper obj = new ObjectMapper();
        obj.getFactory().configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        return obj.writeValueAsString(arg);
    }
}
