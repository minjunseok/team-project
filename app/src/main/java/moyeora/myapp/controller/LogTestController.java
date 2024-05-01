package moyeora.myapp.controller;

import lombok.extern.slf4j.Slf4j;
import moyeora.myapp.controller.test.TestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j(topic = "kafka-elk-test-log")
public class LogTestController {
    @Autowired
    private TestProducer testProducer;

    @GetMapping("logs")
    public String logs() throws Exception {
        testProducer.create();
        return "{'message' : 'hello'}";
    }
}
