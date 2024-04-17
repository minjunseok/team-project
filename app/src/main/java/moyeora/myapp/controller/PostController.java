package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.CommentService;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.util.FileUploadHelper;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Comment;
import moyeora.myapp.vo.Post;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

  //  private static final Log log = LogFactory.getLog(PostController.class);
  private final PostService postService;
  private final FileUploadHelper fileUploadHelper;
  private final CommentService commentService;
  private final SchoolUserService schoolUserService;
  private String uploadDir = "post/";
  private static final Log log = LogFactory.getLog(PostController.class);

  @Value("${ncp.storage.bucket}")
  private String bucketName;

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
//
//    Post old = postService.get(post.getNo());
//    if (old == null) {
//      throw new Exception("번호가 유효하지 않습니다.");
//
//    } else if (old.getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }

    // 파일 업로드 및 AttachedFile 생성
//    List<AttachedFile> files = new ArrayList<>();
//    for (MultipartFile file : attachedFiles) {
//        if (file.getSize() == 0) {
//            continue;
//        }
//        String filename = fileUploadHelper.upload(this.bucketName, this.uploadDir, file);
    // AttachedFile 객체 생성 후 파일 이름 설정
//        AttachedFile attachedFile = new AttachedFile();
//        attachedFile.setFileName(filename);
//        files.add(attachedFile);
//    }


    // Post 객체에 AttachedFile 추가
//    post.setFiles(files);

    // 나머지 처리 코드
    post.setCreatedAt(new Date());
    postService.add(post);

    return "redirect:list?schoolNo=" + post.getSchoolNo();
  }


  @GetMapping("list")
  public void list(Model model, @RequestParam("schoolNo") int schoolNo) {
    model.addAttribute("postlist", postService.findBySchoolPostList(schoolNo));
    model.addAttribute("schoolUsers", schoolUserService.findBySchoolUserList(schoolNo));
  }

  @GetMapping("view/{lNo}")
  public ModelAndView findByPost(ModelAndView model, int no, @PathVariable String lNo) throws Exception {
    int schoolNo = Integer.parseInt(lNo);
    log.debug(postService.get(no, schoolNo));
    Post post = postService.get(no, schoolNo);
    List<AttachedFile> attachedFiles = postService.getAttachedFiles(no);
    List<Comment> comments = postService.getComments(no);

    if (post == null) {
      throw new Exception("게시글 번호가 유효하지 않습니다.");
    }
    System.out.println(post);
    System.out.println(attachedFiles);
    System.out.println(comments);

    model.addObject("comments", comments);
    model.addObject("files", attachedFiles);
    model.addObject("post", post);
    model.setViewName("post/view");
    return model;
  }

//@RequestMapping("list")
//public String list(
//        @RequestParam(defaultValue = "1") int categoryNo,
//        Model model,
//        Integer schoolNo) throws Exception {
//  model.addAttribute("postList", postService.findAll(categoryNo));
//  model.addAttribute("postNo",  categoryNo == 0 ? "일반" : "공지");
//  model.addAttribute("categoryNo",  categoryNo);
//
//        // 학교 번호를 모델에 추가
//        model.addAttribute("schoolNo", schoolNo); // 학교 번호를 가져온다.
//
//  return "post/list";
//}


  // 검색창에 필터로 검색했을 때
  @PostMapping("search")
  public String searchPostsByContent(
          int schoolNo,
          @RequestParam("keyword") String keyword,
          @RequestParam("filter") String filter,
          Model model) {
    if (filter.equals("0")) { // 내용으로 검색
      List<Post> postList = postService.findBySchoolContent(schoolNo, keyword);
      model.addAttribute("postList", postList);
    } else {                  // 작성자로 검색
      List<Post> postList = postService.findBySchoolUserName(schoolNo, keyword);
      model.addAttribute("postList", postList);
    }
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

    return "redirect:list?schoolNo=" + post.getSchoolNo();

  }


  @GetMapping("delete")
  public String delete(
          Post post,
          @RequestParam("post_no") int no,
          @RequestParam("schoolNo") int schoolNo,
          HttpSession session) throws Exception {


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

    postService.delete(no, schoolNo);

//    for (AttachedFile file : files) {
    //fileUploadHelper.delete(this.bucketName, this.uploadDir, file.getFilePath());
//    }

    return "redirect:list?schoolNo=" + post.getSchoolNo();
  }


  @GetMapping("md")
  public String md(
          int no,
          @RequestParam("schoolNo") int schoolNo,
//          @RequestParam(required = false) Integer schoolNo, // Integer로 변경하여 널 허용 (로그인 구현되면 필요 없음)
          Model model) throws Exception {
    Post post = postService.get(no, schoolNo);
    model.addAttribute("post", post);
    model.addAttribute("content", post.getContent()); // "content" 데이터 추가
    return "post/md";
  }


////  @GetMapping("file/delete")
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


//  @GetMapping("/post/{schoolNo}")
//  public String getPostsBySchool(@PathVariable int schoolNo, Model model) {
//    List<Post> posts = postService.findBySchool(schoolNo);
//    model.addAttribute("posts", posts);
//    return "post/list";
//  }

}