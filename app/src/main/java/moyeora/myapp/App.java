package moyeora.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
<<<<<<< HEAD
import org.springframework.stereotype.Controller;
=======
>>>>>>> ffc3c4928967e10d6d928922b37b3f16470e809c
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableTransactionManagement
<<<<<<< HEAD
@PropertySource({
    "classpath:config/ncp.properties",
    "classpath:config/ncp-secret.properties"
})
@Controller
=======
@PropertySource({"classpath:ncp-storage.properties","classpath:ncp-secret.properties"})
>>>>>>> ffc3c4928967e10d6d928922b37b3f16470e809c
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
