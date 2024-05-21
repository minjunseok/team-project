package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.service.*;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolTag;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/school")
public class SchoolController implements InitializingBean {

  private final TagService tagService;
  private final SchoolService schoolService;
  private final PostService postService;
  private final FileUpload fileUpload;
  private final UserService userService;
  private final SchoolAdminService schoolAdminService;
  private final String uploadDir = "school/";

  @Value("${ncp.storage.bucket}")
  private String bucketName;
  final static Log log = LogFactory.getLog(SchoolController.class);


  @Override
  public void afterPropertiesSet() throws Exception {
    log.debug(String.format("uploadDir: %s", this.uploadDir));
  }

  @PostMapping("add")
  public String add(School school, MultipartFile file, @LoginUser User loginUser) throws Exception{
    log.debug("============sdfsdsdf");
    if(file.getSize() > 0) {
      String filename = fileUpload.upload(this.bucketName, this.uploadDir,file);
      school.setPhoto(filename);
    }
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ school.getTags());
    schoolService.add(school, loginUser.getNo(), loginUser.getNo());

    return "redirect:/post/list?schoolNo="+school.getNo();
  }


  @GetMapping("/schoolInfo")
  @ResponseBody
  public Object schoolInfo(Model model, int schoolNo) {


    School school = schoolAdminService.getSchool(schoolNo);
    log.debug(school.getTags().getFirst().getSchoolNo());

    List<SchoolTag> schooltags = school.getTags();
    HashMap<Integer, SchoolTag> schoolTagMap = new HashMap<>();
    for (SchoolTag schoolTag : schooltags) {
      schoolTagMap.put(schoolTag.getTagNo(), schoolTag);
    }

    System.out.println(school);
    model.addAttribute("school", school);
    model.addAttribute("schoolTagMap", schoolTagMap);
    model.addAttribute("tags", tagService.findAllTag());
    for (SchoolTag tag : school.getTags()) {
      System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + tag.getTagNo());
    }

    return school;
  }


  @GetMapping("view")
  public void view() {

  }
  @GetMapping("form")
  public void form(Model model) {
    model.addAttribute("tags", tagService.findAllTag());
  }

  @GetMapping("search")
  @ResponseBody
  public List<School> search(Model model,
      @RequestParam String name) {
    System.out.println(name);
    return schoolService.findBySchoolName(name);
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


//  @GetMapping("postlist")
//  public void findBySchoolPostList(Model model) {
//    model.addAttribute("postliist",postService.findBySchoolPostList());
//  }
}


