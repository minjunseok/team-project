package moyeora.myapp.controller;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
  final static Log log = LogFactory.getLog(MainPageController.class);

  @GetMapping("/index")
  public void index(Model model, String date) {
    model.addAttribute("schools",schoolService.findByUserNo(1).getSchools());
    model.addAttribute("weeks", schoolService.findWeek());
    List<Integer> catelist = new ArrayList<>(Arrays.asList(1,2,3));
  }

}
