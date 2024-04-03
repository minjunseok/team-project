package moyeora.myapp.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.MyPageService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.vo.Post;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
  private final SchoolService schoolService;
  private final MyPageService myPageService;

  final static Log log = LogFactory.getLog(MyPageController.class);


  @GetMapping("newpost")
  @ResponseBody
  public ResponseEntity<List<Post>> newPost() {
    return ResponseEntity.status(201).body(myPageService.findNewPost(1));
  }

  @GetMapping("hotpost")
  @ResponseBody
  public ResponseEntity<List<Post>> hotPost() {
    //인기글 목록
    //태그가 같은 사람들, 팔로워가 많은 사람들 순서대로 출력
    return null;
  }
  @GetMapping("fpost")
  @ResponseBody()
  public ResponseEntity<List<Post>> followingPost(Model model) {
    model.addAttribute("newPosts",myPageService.findNewPost(1));
    return null;
  }

  @GetMapping("mypost")
  public void mypost(Model model) {
    model.addAttribute("newPosts",myPageService.findNewPost(1));
    model.addAttribute("schools",schoolService.findByUserNo(1).getSchools());
  }



}
