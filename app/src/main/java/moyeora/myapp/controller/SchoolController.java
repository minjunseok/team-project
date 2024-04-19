package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.TagService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.School;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/school")
public class SchoolController {

  private final TagService tagService;
  private final SchoolService schoolService;
  private final PostService postService;
  private final FileUpload fileUpload;
  private final UserService userService;

  private String uploadDir;
  @Value("${ncp.storage.bucket}")
  private String bucketName;
  final static Log log = LogFactory.getLog(SchoolController.class);



  @PostMapping("add")
  public String add(School school, MultipartFile file) throws Exception{

    log.debug("============sdfsdsdf");
    if(file.getSize() > 0) {
      String filename = fileUpload.upload(this.bucketName, this.uploadDir,file);
      school.setPhoto(filename);
    }

    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ school.getTags());
    schoolService.add(school, 2, 2);
    return "redirect:list";

  }
  @GetMapping("view")
  public void view() {

  }
  @GetMapping("form")
  public void form(Model model) {
    model.addAttribute("tags", tagService.findAllTag());
  }

  @GetMapping("search")
  public void search(Model model,
      @RequestParam String name) {
    System.out.println(name);
    model.addAttribute("name", schoolService.findBySchoolName(name));
  }

  @GetMapping("schoolUserLevel")
  public void schoolUserLevel(Model model, int userNo){
    model.addAttribute("grade",schoolService.schoolUserLevelCount(userNo));
  }


  @GetMapping("/checkDuplicateSchoolName")
  @ResponseBody
  public Map<String, Integer> checkDuplicateSchoolName(String schoolName) {
    int count = schoolService.isNameExists(schoolName);

    Map<String, Integer> response = new HashMap<>();
    response.put("cnt", count);
    return response;
  }
//  @PostMapping("checkDuplicateSchoolName")
//  public String checkDuplicateSchoolName(@RequestParam("name") String name) {
//    int count = schoolService.isNameExists(name);
//    if (count > 0) {
//      return "사용불가능한 스쿨명입니다.";
//    } else {
//      return "사용가능한 스쿨명입니다.";
//    }
//  }
//  @GetMapping("postlist")
//  public void findBySchoolPostList(Model model) {
//    model.addAttribute("postliist",postService.findBySchoolPostList());
//  }
}


