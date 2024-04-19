package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.CommentService;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.util.FileUploadHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schooluser")
public class SchoolUserController {

  //  private static final Log log = LogFactory.getLog(PostController.class);
  private final PostService postService;
  private final SchoolUserService schoolUserService;
  private final FileUploadHelper fileUploadHelper;
  private final CommentService commentService;
  private String uploadDir = "post/";
  private static final Log log = LogFactory.getLog(SchoolUserController.class);

  @Value("${ncp.storage.bucket}")
  private String bucketName;

//  @GetMapping("form")
//  public void form(int category, Model model) throws Exception {
//   model.addAttribute("postNo", category == 1 ? "일반" : "공지");
//    model.addAttribute("category", category);
//  }

//  @GetMapping("list")
//  public void list(Model model, int schoolNo) {
//    System
//        .out.println(schoolUserService.findBySchoolUserList(schoolNo));
//    log.debug(schoolUserService.findBySchoolUserList(schoolNo));
//
//    model.addAttribute("schoolusers", schoolUserService.findBySchoolUserList(schoolNo));
//  }
}