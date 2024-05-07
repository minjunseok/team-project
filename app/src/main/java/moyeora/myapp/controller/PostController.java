package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dto.AjaxResponse;
import moyeora.myapp.security.PrincipalDetails;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.*;

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


//스쿨 공지를 다루는 메서드


//  @PostMapping("fix")
//  @ResponseBody
//  public Object fixedPost(
//         @RequestBody Post post) throws Exception {
//
//
//
//      postService.fixedPost(post);
//      log.debug(postService.fixedPost(post) + "@@@@@@@@@@@@@@@@@@");
//
//      return AjaxResponse.builder().status("success").build();
//    }

  @PostMapping("commentAdd")
  public AjaxResponse commentAdd(
          @RequestParam String content,
          @RequestParam int postNo,
          Comment comment,
          HttpSession httpSession,
          @AuthenticationPrincipal PrincipalDetails principalDetails,
          @LoginUser User loginUser) throws Exception {

    try {

      comment.setPostNo(postNo);
      comment.setContent(content);
      comment.setContent("test");
      comment.setUserNo(loginUser.getNo());
      log.debug(httpSession.getAttribute("loginUser") + "@@@@@@@@@@@@@@@@@@");

      commentService.add(comment);

      // 성공적으로 고정을 등록했을 경우 응답을 생성합니다.
      return AjaxResponse.builder().status("success").build();
    } catch (Exception e) {
      // 글 고정에 실패한 경우 예외를 처리하고 에러 응답을 생성합니다.
      return AjaxResponse.builder().status("error").message("Failed to add comment").build();
    }
  }


  //  @GetMapping("list4")
//  public void list4(Model model, int schoolNo,
//                    HttpSession httpSession,
//                    @AuthenticationPrincipal PrincipalDetails principalDetails,
//                    @LoginUser User loginUser) {
//    httpSession.getAttribute("loginUser");
//    log.debug("@@@@@@@@@@@@@@@@@" + loginUser.getNo());
//
//    log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + loginUser);
  @PostMapping("fix")
  @ResponseBody
  public AjaxResponse fixedPost(Post post,
                                HttpSession httpSession,
                                @AuthenticationPrincipal PrincipalDetails principalDetails,
                                @LoginUser User loginUser) {


    loginUser.getNo();
    int userNo = post.getNo();
    int schoolNo = postService.findByPostSchoolNo(post.getNo());


    log.debug("schoolNo + @@@@@@@@@@@@@@@@@@@" + schoolNo);
    log.debug("postNo + @@@@@@@@@@@@@@@@@@@@@" + userNo);
    log.debug("sessionNo + @@@@@@@@@@@@@@@@@@@@@" + loginUser.getNo());


    int lNo = schoolUserService.findByUserLevelNo(schoolNo, userNo);

    log.debug("levelNo + @@@@@@@@@@@@@@@@@@@@@@@@" + lNo);


    try {

      if (lNo == 3 || lNo == 4) {



        // 메서드를 호출하여 글 고정을 하고 결과를 받아옵니다.
        postService.fixedPost(post);

        // 성공적으로 고정을 등록했을 경우 응답을 생성합니다.
        return AjaxResponse.builder().status("success").build();
      }
    } catch (Exception e) {
      // 글 고정에 실패한 경우 예외를 처리하고 에러 응답을 생성합니다.
    }
    return AjaxResponse.builder().status("error").message("Failed to fix post").build();
  }



  @PostMapping("fixCancel")
  @ResponseBody
  public AjaxResponse fixedCancel(Post post) {
    try {
      //메서드를 호출하여 글 고정 취소를 진행하고 결과를 받아옵니다.
      postService.fixedCancel(post);

      //
      return AjaxResponse.builder().status("success").build();
    } catch (Exception e) {
      // 고정 취소에 실패한 경우 예외를 처리하고 에러 응답을 생성합니다.
      return AjaxResponse.builder().status("error").message("Failed to fix cancel post").build();
    }
  }





  @GetMapping("noticelist")
  public void list(Model model, int schoolNo) {


    System
            .out.println(postService.findByNotice(schoolNo));
    log.debug(postService.findByNotice(schoolNo));

    log.debug(schoolUserService.findBySchoolUserList(schoolNo));
    System.out.println(schoolUserService.findBySchoolUserList(schoolNo));
    List<Post> post = postService.findByNotice(schoolNo);

    System.out.print("@@@@@@@@@@@@@@@@@@@" + post);
    model.addAttribute("schoolNo", schoolNo);
    model.addAttribute("noticelist", post);
    model.addAttribute("schoolUsers", schoolUserService.findBySchoolUserList(schoolNo));
  }


//공지글 리스트 url
  @GetMapping("noticeview/{lNo}")
  @ResponseBody
  public Object findByNotice(int no, @PathVariable String lNo) throws Exception {

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

    Map<String,Object> result = new HashMap<>();
    result.put("comments", comments);
    result.put("files", attachedFiles);
    result.put("post", post);
    return result;
  }

//공지게시판
  @PostMapping("noticeadd")
  public String addNotice(
          Post post,
          MultipartFile[] files,
          HttpSession session) throws Exception {

    // 파일 업로드 및 AttachedFile 생성
    List<AttachedFile> attachedFiles = new ArrayList<>();
    for (MultipartFile file : files) {
      if (file.getSize() == 0) {
        continue;
      }
      String filename = fileUploadHelper.upload(this.bucketName, this.uploadDir, file);
      // AttachedFile 객체 생성 후 파일 이름 설정
      AttachedFile attachedFile = new AttachedFile();
      attachedFile.setFileName(filename);
      attachedFiles.add(attachedFile);
    }





    // 'created_at' 필드에 현재 시간 설정
    post.setCreatedAt(new Date()); // 이 코드는 java.util.Date를 import 해야 합니다.

    // 나머지 처리 코드
    post.setCreatedAt(new Date());
    postService.addNotice(post);

    return "redirect:noticelist?schoolNo=" + post.getSchoolNo();
  }


  //


  @PostMapping("add")
  public String add(
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

    // 파일 업로드 및 AttachedFile 생성
    List<AttachedFile> attachedFiles = new ArrayList<>();
    for (MultipartFile file : files) {
      if (file.getSize() == 0) {
        continue;
      }
      String filename = fileUploadHelper.upload(this.bucketName, this.uploadDir, file);
      // AttachedFile 객체 생성 후 파일 이름 설정
      AttachedFile attachedFile = new AttachedFile();
      attachedFile.setFileName(filename);
      attachedFiles.add(attachedFile);
    }





    // 'created_at' 필드에 현재 시간 설정
    post.setCreatedAt(new Date()); // 이 코드는 java.util.Date를 import 해야 합니다.

    // 나머지 처리 코드
    post.setCreatedAt(new Date());
    postService.add(post);

    return "redirect:list?schoolNo=" + post.getSchoolNo();
  }





  @GetMapping("list4")
  public void list4(Model model, int schoolNo,
                    HttpSession httpSession,
                    @AuthenticationPrincipal PrincipalDetails principalDetails,
                    @LoginUser User loginUser
  ) {

    System.out.println(postService.findBySchoolPostList(schoolNo));
    log.debug(postService.findBySchoolPostList(schoolNo));

    log.debug(schoolUserService.findBySchoolUserList(schoolNo));
    System.out.println(schoolUserService.findBySchoolUserList(schoolNo));
    List<Post> posts = postService.findBySchoolPostList(schoolNo);
    Post post = postService.findByFixList(schoolNo);

    System.out.print("@@@@@@@@@@@@@@@@@@@" + posts);
    model.addAttribute("schoolNo", schoolNo);
    model.addAttribute("postlists", posts);
    model.addAttribute("schoolUsers", schoolUserService.findBySchoolUserList(schoolNo));
    model.addAttribute("fixlist", post);
  }


  @GetMapping("view2/{lNo}")
  @ResponseBody
  public Object findByPost2(int no, @PathVariable String lNo) throws Exception {

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

    Map<String,Object> result = new HashMap<>();
    result.put("comments", comments);
    result.put("files", attachedFiles);
    result.put("post", post);
    return result;
  }


//  @GetMapping("view/{lNo}")
//  @ResponseBody
//  public Object findByPost(int no, @PathVariable String lNo) throws Exception {
//
//    int schoolNo = Integer.parseInt(lNo);
//    log.debug(postService.get(no, schoolNo));
//    Post post = postService.get(no, schoolNo);
//    List<AttachedFile> attachedFiles = postService.getAttachedFiles(no);
//    List<Comment> comments = postService.getComments(no);
//
//    if (post == null) {
//      throw new Exception("게시글 번호가 유효하지 않습니다.");
//    }
//    System.out.println(post);
//    System.out.println(attachedFiles);
//    System.out.println(comments);
//
//    Map<String,Object> result = new HashMap<>();
//    result.put("comments", comments);
//    result.put("files", attachedFiles);
//    result.put("post", post);
//    return result;
//  }

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