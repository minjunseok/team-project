package moyeora.myapp;

import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@Controller
@PropertySource({
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

//  @GetMapping("/home")
//  public void home(HttpSession session) {
//    User user = userService.get(1);
//    session.setAttribute("loginUser", user);
//  }

  @GetMapping("/about")
  public void about() {
  }
}
