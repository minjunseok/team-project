/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package moyeora.myapp;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.ibatis.javassist.Loader.Simple;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@SpringBootApplication
@EnableTransactionManagement
@PropertySource({
    "classpath:config/ncp.properties",
    "classpath:config/ncp-secret.properties"
})
@Controller
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("##moyora test main() exec##");
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
//        System.out.println(c);
//        System.out.println(c.getTime());
//        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//        c.add(Calendar.DAY_OF_YEAR,1);
//        System.out.println(c.getTime());
//        String k = dateformat.format(c.getTime());
//        System.out.println(k);
        SpringApplication.run(App.class, args);
    }
    @GetMapping("/home")
    public void home() {
    }
}
