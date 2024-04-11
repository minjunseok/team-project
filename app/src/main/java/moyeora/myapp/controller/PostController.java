package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.PostService;
import moyeora.myapp.util.FileUploadHelper;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Post;
import moyeora.myapp.vo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

//  private static final Log log = LogFactory.getLog(PostController.class);
  private final PostService postService;
  private final FileUploadHelper fileUploadHelper;
  private String uploadDir = "post/";

  @Value("${ncp.storage.bucket}")
  private String bucketName;


//  @GetMapping("form")
//  public void form(int category, Model model) throws Exception {
//   model.addAttribute("postNo", category == 1 ? "일반" : "공지");
//    model.addAttribute("category", category);
//  }

  @GetMapping("form")
  public void form() throws Exception {

  }



  @PostMapping("add")
  public String add(
          Post post,
          MultipartFile[] attachedFiles,
          HttpSession session) throws Exception {

//    User loginUser = (User) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }
//    post.setUserNo(loginUser);

//    ArrayList<AttachedFile> files = new ArrayList<>();
//    if (post.getCategoryNo() == 1) {
//      for (MultipartFile file : attachedFiles) {
//        if (file.getSize() == 0) {
//          continue;
//        }
//        String filename = storageService.upload(this.bucketName, this.uploadDir, file);
//        files.add(AttachedFile.builder().filePath(filename).build());
//      }
//    }
//    if (files.size() > 0) {
//      post.setFileList(files);
//    }

     // 'created_at' 필드에 현재 시간 설정
    post.setCreatedAt(new Date()); // 이 코드는 java.util.Date를 import 해야 합니다.

    postService.add(post);

    return "redirect:list";
  }



//@GetMapping("list")
@RequestMapping("list")
public String list(
        @RequestParam(defaultValue = "1") int categoryNo,
        Model model) throws Exception {
  model.addAttribute("post", postService.findAll(categoryNo));
  model.addAttribute("postNo",  categoryNo == 0 ? "일반" : "공지");
  model.addAttribute("categoryNo",  categoryNo);
  return "post/list";
}



  @PostMapping("search")
  public String findByPost(String content, Model model) {
    model.addAttribute("search", postService.findByPost(content));
    return "post/list";
  }

  @PostMapping("update")
  public String update(
          Post post,
          MultipartFile[] attachedFiles,
          HttpSession session) throws Exception {

//    User loginUser = (User) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }
//
//    Post old = postService.get(post.getNo());
//    if (old == null) {
//      throw new Exception("번호가 유효하지 않습니다.");
//
//    } else if (old.getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }
//
//    ArrayList<AttachedFile> files = new ArrayList<>();
//    if (post.getCategoryNo() == 1) {
//      for (MultipartFile file : attachedFiles) {
//        if (file.getSize() == 0) {
//          continue;
//        }
//        String filename = fileUploadHelper.upload(this.bucketName, this.uploadDir, file);
//        files.add(AttachedFile.builder().filePath(filename).build());
//      }
//    }
//    if (files.size() > 0) {
//      post.setFileList(files);
//    }

    postService.update(post);

    return "redirect:list";

  }

  @GetMapping("md")
  public String md(int no, Model model) throws Exception {
    Post post = postService.get(no);
    model.addAttribute("post", post);
     model.addAttribute("content", post.getContent()); // "content" 데이터 추가
    return "post/md";
  }


  @GetMapping("delete")
  public String delete(
         @RequestParam("post_no") int no) throws Exception {
// int category,  HttpSession session 파라미터


//    User loginUser = (User) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }
//
//    Post post = postService.get(no);
//    if (post == null) {
//      throw new Exception("번호가 유효하지 않습니다.");
//
//    } else if (post.getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }

//    List<AttachedFile> files = postService.getAttachedFiles(no);

    postService.delete(no);

//    for (AttachedFile file : files) {
      //fileUploadHelper.delete(this.bucketName, this.uploadDir, file.getFilePath());
//    }

    return "redirect:list";
  }

//  @GetMapping("file/delete")
//  public String fileDelete(int category, int no, HttpSession session) throws Exception {
//
//    User loginUser = (User) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }
//
//    AttachedFile file = postService.getAttachedFile(no);
//    if (file == null) {
//      throw new Exception("첨부파일 번호가 유효하지 않습니다.");
//    }
//
//    User writer = postService.get(file.getPostNo()).getWriter();
//    if (writer.getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }
//
//    postService.deleteAttachedFile(no);
//
//    //fileUploadHelper.delete(this.bucketName, this.uploadDir, file.getFilePath());
//
//    return "redirect:../view?category=" + category + "&no=" + file.getPostNo();
//  }

}