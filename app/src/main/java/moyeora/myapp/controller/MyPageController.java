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
  @GetMapping("hotpost")
  @ResponseBody
  public ResponseEntity<List<Post>> hotPost() {
    return ResponseEntity.ok(myPageService.findHotPost(1));
  }

  @GetMapping("followingpost")
  @ResponseBody()
  public ResponseEntity<List<Post>> followingPost( ) {
    return null;
  }

  @GetMapping("newpost")
  @ResponseBody()
  public ResponseEntity<List<Post>> newPost( ) {
    return ResponseEntity.ok(myPageService.findNewPost(1));
}

  @GetMapping("mypost")
  public void myPost(Model model) {
    model.addAttribute("newPosts",myPageService.findNewPost(1));
    model.addAttribute("schools",schoolService.findByUserNo(1).getSchools());
  }
}
