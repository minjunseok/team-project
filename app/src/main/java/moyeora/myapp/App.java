package moyeora.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@Controller
@PropertySource({
        "classpath:config/oauth.properties",
//  "file:${user.home}/ncp-storage.properties",
//  "classpath:ncp-secret.properties",
//  "file:${user.home}/bootpay.properties",
//  "file:${user.home}/config/email.properties"
        "classpath:ncp-storage.properties",
        "classpath:ncp-secret.properties",
        "classpath:bootpay.properties",
        "classpath:config/email.properties"
})
public class App {
  public static void main(String[] args) throws Exception {
    System.out.println("과제관리 시스템 서버 실행!");
    SpringApplication.run(App.class, args);
  }

  @GetMapping("/home")
  public void home() {
  }

  @GetMapping("/about")
  public void about() {
  }
}
