package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.CommentService;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.service.UserService;
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
@RequestMapping("/schoolUser")
public class SchoolUserController {

  //  private static final Log log = LogFactory.getLog(PostController.class);
  private final PostService postService;
  private final SchoolUserService schoolUserService;
  private final FileUploadHelper fileUploadHelper;
  private final CommentService commentService;
  private UserService userService;
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
          @RequestParam(value = "userNo", required = false) int userNo,
          @RequestParam(value = "schoolNo") int schoolNo
          /*@RequestParam("schoolNo") int schoolNo*/) {
    try {
      // 1. UserService를 통해 유저 정보 조회
      User user = userService.get(userNo);
      if (user == null) {
        return "error: 유저를 찾을 수 없습니다.";
      }
      log.debug(schoolNo);

      // 2. 스쿨 유저 추가
      SchoolUser schoolUser = new SchoolUser();
      schoolUser.setUserNo(64);
      schoolUser.setSchoolNo(1); // 예시로 스쿨 번호 1을 사용합니다.
      schoolUser.setLevelNo(1); // 레벨 1로 설정

      schoolUserService.addSchoolUser(userNo);

      return "success: 스쿨 가입이 완료되었습니다.";
    } catch (Exception e) {
      e.printStackTrace();
      return "error: 스쿨 가입 중 오류가 발생했습니다.";
    }
  }
}



