package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.TagService;
import moyeora.myapp.vo.School;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/school")
public class SchoolController {

  private final TagService tagService;
  private final SchoolService schoolService;
  private final PostService postService;
  final static Log log = LogFactory.getLog(SchoolController.class);



  @GetMapping("form")
  public void form(Model model, int schoolNo) {
    model.addAttribute("postlists",postService.findBySchoolPostList(schoolNo));
  }

  @PostMapping("add")
  public void add(School school) {
    log.debug("============sdfsdsdf");
  }

  @GetMapping("view")
  public void view() {

  }
//  @GetMapping("form")
//  public void form(Model model) {
//    model.addAttribute("tags", tagService.findAllTag());

//  }

  @GetMapping("search")
  public void search(Model model,
      @RequestParam String name) {
    System.out.println(name);
    model.addAttribute("name", schoolService.findBySchoolName(name));

  }

//  @GetMapping("postlist")
//  public void findBySchoolPostList(Model model) {
//    model.addAttribute("postliist",postService.findBySchoolPostList());
//  }
}


