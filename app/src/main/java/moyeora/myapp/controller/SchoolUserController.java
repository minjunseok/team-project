package moyeora.myapp.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.CommentService;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.util.FileUploadHelper;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Comment;
import moyeora.myapp.vo.Post;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

  @GetMapping("list")
  public void list(Model model, int no) {
    System
        .out.println(schoolUserService.findBySchoolUserList(no));
    log.debug(schoolUserService.findBySchoolUserList(no));

    model.addAttribute("schoolusers", schoolUserService.findBySchoolUserList(no));
  }
}