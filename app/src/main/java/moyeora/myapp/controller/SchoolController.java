package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.TagService;
import moyeora.myapp.vo.School;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/school")
public class SchoolController {

  private final TagService tagService;
  final static Log log = LogFactory.getLog(SchoolController.class);
  @PostMapping("add")
  public void add(School school) {
    log.debug("============sdfsdsdf");
  }

  @GetMapping("view")
  public void view() {

  }
  @GetMapping("form")
  public void form(Model model) {
    model.addAttribute("tags",tagService.findAllTag());
  }
}
