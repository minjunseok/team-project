package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.security.PrincipalDetails;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.vo.Post;
import moyeora.myapp.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class PostSearchController {

    private final SchoolUserService schoolUserService;
    private final PostService postService;

    // 검색창에 필터로 검색했을 때
    @PostMapping("search")
    @GetMapping("search")
    public String searchPosts(
            @RequestParam("schoolNo") int schoolNo,
            @RequestParam("keyword") String keyword,
            @RequestParam("filter") String filter,
            @LoginUser User loginUser,
            Model model) {

        if (loginUser != null) {
            int memberCheck = schoolUserService.findByMemberCheck(schoolNo, loginUser.getNo());

            if (memberCheck == 1) {

                int userNo = loginUser.getNo();

                //  회원이고 해당 학교의 회원인 경우
                int accessLevel = schoolUserService.findLevel(schoolNo, userNo);
                model.addAttribute("sender", loginUser);
                model.addAttribute("accessLevel", accessLevel);
                model.addAttribute("loginUser", loginUser);
            }
        }


        if (filter.equals("0")) { // 내용으로 검색
            List<Post> posts = postService.findBySchoolContent(schoolNo, keyword);
            Post post = postService.findByFixList(schoolNo);
            model.addAttribute("postlist", posts);
            model.addAttribute("fixlist", post);
        } else {                  // 작성자로 검색
            List<Post> posts = postService.findBySchoolUserName(schoolNo, keyword);
            Post post = postService.findByFixList(schoolNo);
            model.addAttribute("postlist", posts);
            model.addAttribute("fixlist", post);
        }

        return "post/list";

    }

    @GetMapping("list")
    public void list(Model model,
                     @RequestParam("schoolNo") int schoolNo,
                     @AuthenticationPrincipal PrincipalDetails principalDetails,
                     @LoginUser User loginUser) {


        if (loginUser != null) {
            int memberCheck = schoolUserService.findByMemberCheck(schoolNo, loginUser.getNo());

            if (memberCheck == 1) {

                int userNo = loginUser.getNo();

                // 회원이고 해당 학교의 회원인 경우
                int accessLevel = schoolUserService.findLevel(schoolNo, userNo);
                model.addAttribute("sender", loginUser);
                model.addAttribute("accessLevel", accessLevel);
                model.addAttribute("loginUser", loginUser);
            }
        }

        // 로그인 상태와 관계없이 공통으로 처리해야 할 부분
        System.out.println(postService.findBySchoolPostList(schoolNo));
        System.out.println(schoolUserService.findBySchoolUserList(schoolNo));
        List<Post> posts = postService.findBySchoolPostList(schoolNo);
        Post post = postService.findByFixList(schoolNo);
        model.addAttribute("schoolNo", schoolNo);
        model.addAttribute("postlist", posts);
        model.addAttribute("schoolUsers", schoolUserService.findBySchoolUserList(schoolNo));
        model.addAttribute("fixlist", post);
    }

}
