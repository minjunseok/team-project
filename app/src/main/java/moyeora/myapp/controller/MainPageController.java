package moyeora.myapp.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.ClassService;
import moyeora.myapp.service.SchoolService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainPageController {

  private final SchoolService schoolService;
  private final ClassService classService;
  final static Log log = LogFactory.getLog(MainPageController.class);
  @GetMapping("/index")
  public void index(Model model ) {
      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식 지정
      String date = currentTime.format(formatter);

    //유저의 밴드 가입 리스트를 가져오기
    model.addAttribute("schools",schoolService.findByUserNo(1).getSchools());
    //해당 날짜주의 월화수목금토일 정보 가져오기
    model.addAttribute("weeks", schoolService.findWeek());
    //해당 날짜의 약속 정보를 전부 가져오기 , 지역 정보 추가해야함
    model.addAttribute("classes", classService.findByDate(date));

    model.addAttribute("hotSchools", schoolService.findHotSchool(1));
  }

}
