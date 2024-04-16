package moyeora.myapp.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.ClassService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.vo.Class;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/class")
@RequiredArgsConstructor
public class  ClassController {

  private final ClassService classService;


  @GetMapping("/list")
  @ResponseBody
  public List<Class> viewOfDate(String date) {
    if(date.matches("\\d{4}-\\d{2}-\\d{2}")) {
      System.out.println("1");
      return classService.findByDate(date);
    } else {
      System.out.println("2");
      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식 지정
      date = currentTime.format(formatter);
      return classService.findByDate(date);
    }
  }



}
