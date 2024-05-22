package moyeora.myapp.aspect;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Aspect
public class ControllerExecutionAspect {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public String className;

    private static final Logger logger = LogManager.getLogger(ControllerExecutionAspect.class);

    @Pointcut("execution(* moyeora.myapp.controller.*.*(..))")
    public void controllerMethod() {
    }

    @Before("controllerMethod()")
    public void logControllerBefore(JoinPoint joinPoint) {
        className = joinPoint.getTarget().getClass().getName();
    }

    @AfterReturning(pointcut = "controllerMethod()", returning = "result")
    public void logControllerMethodCall(JoinPoint joinPoint, Object result) throws Exception {
        String methodName = joinPoint.getSignature().getName();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 요청된 URL 확인
        String url = request.getRequestURL().toString();
        System.out.println("Requested URL: " + url);
        Map<String, Object> map = new HashMap<>();
        ObjectMapper obj = new ObjectMapper();
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String str = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("execute", "controller");
        map.put("url", url);
        map.put("email", str);
        map.put("class", className);
        map.put("method", methodName);
        //map.put("result", result);
        if (request.getParameter("schoolNo") != null) {
            map.put("schoolno", request.getParameter("schoolNo"));
        }
        if (request.getParameter("postNo") != null) {
            map.put("postno", request.getParameter("postNo"));
        }
        kafkaTemplate.send("kafka-elk-test-log", map);
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
        return obj.writeValueAsString(arg);
    }
}
