package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.CommentService;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.util.FileUploadHelper;
import moyeora.myapp.vo.Post;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

  // 사용자 가입 처리를 담당하는 메서드
  @PostMapping("/addSchoolUser")
  @ResponseBody
  public String addSchoolUser(
          SchoolUser schoolUser) {
    try {
         // 사용자를 가입시키고 레벨을 1로 설정
    schoolUserService.addSchoolUser(schoolUser);
      return "success"; // 가입 성공시 success 반환
    } catch (Exception e) {
      log.error("가입 처리 중 오류 발생: " + e.getMessage());
      return "error"; // 가입 실패시 error 반환
    }

  }

}