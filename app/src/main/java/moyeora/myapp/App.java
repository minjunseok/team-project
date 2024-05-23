package moyeora.myapp;

import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.annotation.LoginUserArgumentResolver;
import moyeora.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@Controller
@PropertySource({
        "file:${user.home}/config/ncp-storage.properties",
        "file:${user.home}/config/ncp-secret.properties",
        "file:${user.home}/config/bootpay.properties",
        "file:${user.home}/config/email.properties",
        "file:${user.home}/config/oauth.properties"
})
public class App implements WebMvcConfigurer {

  @Autowired
  LoginUserArgumentResolver loginUserArgumentResolver;

  public static void main(String[] args) throws Exception {
    System.out.println("과제관리 시스템 서버 실행!");

    SpringApplication.run(App.class, args);
  }

  @GetMapping("/home")
  public void home(@LoginUser User loginUser, Model model) {
    model.addAttribute("loginUser", loginUser);
  }

  @GetMapping("/about")
  public void about() {
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginUserArgumentResolver);
  }
}
