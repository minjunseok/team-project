package moyeora.myapp.controller;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
@SessionAttributes("attachedFiles")
public class PostController {

  //  private static final Log log = LogFactory.getLog(PostController.class);
  private final PostService postService;
  private final FileUploadHelper fileUploadHelper;
  private final CommentService commentService;
  private final SchoolUserService schoolUserService;
  private  final FileUploadHelper FileNameGenerator;
  private String uploadDir = "post/";
  private static final Log log = LogFactory.getLog(PostController.class);


  @Value("${ncp.storage.bucket}")
  private String bucketName;

  @GetMapping("form")
  public void form() throws Exception {

  }

//  @PostMapping("add")
//  public String add(
//          Post post,
//          HttpSession session,
//          SessionStatus sessionStatus) throws Exception {
//
////    User loginUser = (User) session.getAttribute("loginUser");
////    if (loginUser == null) {
////      throw new Exception("로그인하시기 바랍니다!");
////    }
////
////    Post old = postService.get(post.getNo());
////    if (old == null) {
////      throw new Exception("번호가 유효하지 않습니다.");
////
////    } else if (old.getNo() != loginUser.getNo()) {
////      throw new Exception("권한이 없습니다.");
////    }
//
//
//    // 게시글 등록할 때 삽입한 이미지 목록을 세션에서 가져온다.
//    List<AttachedFile> fileList = (List<AttachedFile>) session.getAttribute("attachedFiles");
//
//
//    if (fileList != null) {
//      for (int i = fileList.size() - 1; i >= 0; i--) {
//        AttachedFile attachedFile = fileList.get(i);
//        if (post.getContent().indexOf(attachedFile.getFilePath()) == -1) {
//          // Object FileUploadHelper에 업로드 한 파일 중에서 게시글 콘텐트에 포함되지 않은 것은 삭제한다.
//          fileUploadHelper.delete(this.bucketName, this.uploadDir, attachedFile.getFilePath());
//          log.debug(String.format("%s 파일 삭제!", attachedFile.getFilePath()));
//          fileList.remove(i);
//        }
//      }
//      if (fileList.size() > 0) {
//        post.setFileList(fileList);
//      }
//    }
//
//
//     // 파일 업로드 로직을 추가해줍니다.
//    for (MultipartFile file : files) {
//        if (!file.isEmpty()) {
//          String filename = FileNameGenerator.generateFileName(file); // 파일 이름 생성
//            String objectName = this.uploadDir + filename;
//            String filePath = this.fileUploadHelper.upload(this.bucketName, objectName, file);
//            AttachedFile attachedFile = new AttachedFile();
//            attachedFile.setFilePath(filePath);
//            fileList.add(attachedFile);
//        }
//    }
//
//
//
//    // 'created_at' 필드에 현재 시간 설정
//    post.setCreatedAt(new Date()); // 이 코드는 java.util.Date를 import 해야 합니다.
//
//    // 나머지 처리 코드
//    post.setCreatedAt(new Date());
//    postService.add(post);
//
//    // 게시글을 등록하는 과정에서 세션에 임시 보관한 첨부파일 목록 정보를 제거한다.
//    sessionStatus.setComplete();
//
//    return "redirect:list?schoolNo=" + post.getSchoolNo();
//  }


 @PostMapping("add")
public String add(
        Post post,
        @RequestParam("files") MultipartFile[] files, // 파일 업로드를 위한 파라미터 추가
        HttpSession session,
        SessionStatus sessionStatus) throws Exception {

    // 파일 업로드 로직을 추가해줍니다.
    List<String> uploadedFiles = new ArrayList<>();
    if (files != null && files.length > 0) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String filename = FileNameGenerator.generateFileName(file); // 파일 이름 생성
                String objectName = this.uploadDir + filename;
                String filePath = this.fileUploadHelper.upload(this.bucketName, objectName, file);
                uploadedFiles.add(filename); // 업로드된 파일 이름 추가
            }
        }
    }

    // 'created_at' 필드에 현재 시간 설정
    post.setCreatedAt(new Date()); // 이 코드는 java.util.Date를 import 해야 합니다.

    // 나머지 처리 코드
    postService.add(post);

    // 업로드된 파일 이름들을 DB에 저장
    for (String uploadedFile : uploadedFiles) {
        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFilePath(uploadedFile);
        post.getFileList().add(attachedFile);
    }

    // 게시글을 등록하는 과정에서 세션에 임시 보관한 첨부파일 목록 정보를 제거한다.
    sessionStatus.setComplete();

    return "redirect:list?schoolNo=" + post.getSchoolNo();
}





  @GetMapping("list")
  public void list(Model model, int schoolNo) {
    System
            .out.println(postService.findBySchoolPostList(schoolNo));
    log.debug(postService.findBySchoolPostList(schoolNo));

    log.debug(schoolUserService.findBySchoolUserList(schoolNo));
    System.out.println(schoolUserService.findBySchoolUserList(schoolNo));
    List<Post> posts = postService.findBySchoolPostList(schoolNo);
//    for (Post post : posts) {
//      List<AttachedFile> attachedFiles = postService.getAttachedFiles(post.getNo());
//      List<Comment> comments = postService.getComments(post.getNo());


    for (Post post : posts) {
//      List<AttachedFile> attachedFiles = postService.getAttachedFiles(schoolNo);
      List<Comment> comments = postService.getComments(schoolNo);
      post.setComments(comments);
    }
    model.addAttribute("postlists", posts);
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
          MultipartFile[] files,
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


    ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
    if (post.getCategoryNo() == 1) {
      for (MultipartFile file : files) {
        if (file.getSize() == 0) {
          continue;
        }
        String filename = fileUploadHelper.upload(this.bucketName, this.uploadDir, file);
        attachedFiles.add(AttachedFile.builder().filePath(filename).build());
      }
    }
    if (attachedFiles.size() > 0) {
      post.setFileList(attachedFiles);
    }
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
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
//
//    List<AttachedFile> files = postService.getAttachedFiles(no);
//
    postService.delete(no, schoolNo);
//
//    for (AttachedFile file : files) {
//    fileUploadHelper.delete(this.bucketName, this.uploadDir, file.getFilePath());
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