package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.CommentService;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.util.FileUploadHelper;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("schoolUser")
public class SchoolUserController {

    //  private static final Log log = LogFactory.getLog(PostController.class);
    private final PostService postService;

    @Autowired
    private final SchoolUserService schoolUserService;
    private final FileUploadHelper fileUploadHelper;
    private final CommentService commentService;

    @Autowired
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
            HttpSession session,
            @RequestBody Map<String, Integer> requestData) throws Exception {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new Exception("로그인하시기 바랍니다!");
        }

        Integer userNo = requestData.get("userNo");
        Integer schoolNo = requestData.get("schoolNo");

        try {
            // 1. UserService를 통해 유저 정보 조회
            User user = userService.get(userNo);
            if (user == null) {
                return "error: 유저를 찾을 수 없습니다.";
            }
            log.debug(schoolNo);
            log.debug(userNo);

            // 2. 스쿨 유저 추가
            SchoolUser schoolUser = new SchoolUser();
            schoolUser.setUserNo(userNo);
            schoolUser.setSchoolNo(schoolNo);
            schoolUser.setLevelNo(1); // 유저의 스쿨 레벨을 1로 설정

            schoolUserService.addSchoolUser(userNo, schoolNo, schoolUser.getLevelNo());

            return "success: 스쿨 가입이 완료되었습니다.";
        } catch (Exception e) {
            e.printStackTrace();
            return "error: 스쿨 가입 중 오류가 발생했습니다.";
        }
    }
}



