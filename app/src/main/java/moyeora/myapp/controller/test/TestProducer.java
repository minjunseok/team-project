package moyeora.myapp.controller.test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j(topic = "kafka-elk-test-log")
public class TestProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TestProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void create() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("message", "ddd");
        JsonMapper jso = new JsonMapper();
        String str = jso.writeValueAsString(map);
        kafkaTemplate.send("kafka-elk-test-log", str);
    }

    @KafkaListener(topics = "kafka-elk-test-log", groupId = "group_1")
    public void listener(Object data) {
        System.out.println(data);
    }
}