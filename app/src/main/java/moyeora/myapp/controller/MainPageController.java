package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.service.SchoolClassService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
@RequestMapping("index")
public class MainPageController {

  private final SchoolService schoolService;
  private final SchoolClassService schoolClassService;
  final static Log log = LogFactory.getLog(MainPageController.class);
  @GetMapping("")
  public ModelAndView index(ModelAndView model, @LoginUser User loginUser) {
      if(loginUser==null || loginUser.getNo() < 1) {
          model.setViewName("/auth/form");
          return model;
      }
      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식 지정
      String date = currentTime.format(formatter);
    //유저의 밴드 가입 리스트를 가져오기
  if(schoolService.findByUserNo(loginUser.getNo())!=null) {
      model.addObject("schools", schoolService.findByUserNo(loginUser.getNo()).getSchools());
  }
    //해당 날짜주의 월화수목금토일 정보 가져오기
    model.addObject("weeks", schoolService.findWeek());
    //해당 날짜의 약속 정보를 전부 가져오기 , 지역 정보 추가해야함
    model.addObject("schoolClasses", schoolClassService.findByDate(date));
    model.addObject("hotSchools", schoolService.findHotSchool(1));
    model.setViewName("/index");
    return model;
  }
}
