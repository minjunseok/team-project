package moyeora.myapp.aspect;

import moyeora.myapp.vo.Post;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ServiceExecutionAspect {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Pointcut("execution(* moyeora.myapp.dao..PostDao.add(..))")
    public void daoMethod() {
    }

    @AfterReturning("daoMethod()")
    public void logDaoBefore(JoinPoint joinPoint) {
        Map<String, Object> map = new HashMap<>();
        String className = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();
        Post post = (Post) args[0];
        map.put("content", post.getContent());
        map.put("no", post.getNo());
        map.put("execution", "post");

        kafkaTemplate.send("kafka-elk-test-log", map);
    }
}
