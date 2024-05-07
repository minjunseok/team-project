package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.util.FileUpload;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
@SessionAttributes("attachedFiles")
public class PostController {

    //  private static final Log log = LogFactory.getLog(PostController.class);
    private final PostService postService;
    private final FileUpload fileUpload;
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
            HttpSession session,
            SessionStatus sessionStatus) throws Exception {

//        User loginUser = (User) session.getAttribute("loginUser");
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


        // 게시글 등록할 때 삽입한 이미지 목록을 세션에서 가져온다.
        List<AttachedFile> attachedFiles = (List<AttachedFile>) session.getAttribute("attachedFiles");


        // attachedFiles가 null이 아닌지 확인
        if (attachedFiles != null) {
            // attachedFiles가 null이 아닐 경우에만 처리

            for (int i = attachedFiles.size() - 1; i >= 0; i--) {
                AttachedFile attachedFile = attachedFiles.get(i);
                if (post.getContent().indexOf(attachedFile.getFilePath()) == -1) {
                    // Object Storage에 업로드 한 파일 중에서 게시글 콘텐트에 포함되지 않은 것은 삭제한다.
                    fileUpload.delete(this.bucketName, this.uploadDir, attachedFile.getFilePath());
                    log.debug(String.format("%s 파일 삭제!", attachedFile.getFilePath()));
                    attachedFiles.remove(i);
                }
            }
            if (attachedFiles.size() > 0) {
                post.setFileList(attachedFiles);
            }
            // 게시글을 등록하는 과정에서 세션에 임시 보관한 첨부파일 목록 정보를 제거한다.
            sessionStatus.setComplete();
        }

        // 'created_at' 필드에 현재 시간 설정
        post.setCreatedAt(new Date()); // 이 코드는 java.util.Date를 import 해야 합니다.

        // 나머지 처리 코드
        log.debug("@@@@@@@===>>" + post);
        postService.add(post);

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

        model.addAttribute("postList", posts);
        model.addAttribute("schoolNo", schoolNo);
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
    @GetMapping("search")
    public String searchPosts(
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


        ArrayList<AttachedFile> fileList = new ArrayList<>();
        if (fileList != null && fileList.size() > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String filename = this.fileUpload.upload(this.bucketName, this.uploadDir, file);
                    fileList.add(AttachedFile.builder().filePath(filename).build()); // 업로드된 파일 이름 추가
                }
            }
        }

        if (fileList.size() > 0) {
            post.setFileList(fileList);
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
        List<AttachedFile> files = postService.getAttachedFiles(no);

        postService.delete(no, schoolNo);

        for (AttachedFile file : files) {
            fileUpload.delete(this.bucketName, this.uploadDir, file.getFilePath());
        }

        return "redirect:list?schoolNo=" + post.getSchoolNo();
    }


    @GetMapping("md/{postNo}")
    public String md(
            @PathVariable("postNo") int no,
            @RequestParam("schoolNo") int schoolNo,
            Model model) throws Exception {
        Post post = postService.findByPost(no, schoolNo);
        model.addAttribute("post", post);
        model.addAttribute("content", post.getContent()); // "content" 데이터 추가


        return "post/md";
    }


    @GetMapping("file/delete")
    public String fileDelete(int no, Post post, HttpSession session) throws Exception {

//    User loginUser = (User) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }

        AttachedFile fileList = postService.getAttachedFile(no);
        if (fileList == null) {
            throw new Exception("첨부파일 번호가 유효하지 않습니다.");
        }

//    User writer = postService.get(fileList.getPostNo()).getWriter();
//    if (writer.getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }

        postService.deleteAttachedFile(no);

        //fileUploadHelper.delete(this.bucketName, this.uploadDir, file.getFilePath());

        return "redirect:list?schoolNo=" + post.getSchoolNo();
    }

    @PostMapping("file/upload")
    @ResponseBody
    public Object fileUpload(
            MultipartFile[] files,
            HttpSession session,
            Model model) throws Exception {

        //    User loginUser = (User) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }

        // FileUpoladHelper Object Storage에 저장한 파일의 이미지 이름을 보관할 컬렉션을 준비한다.
        ArrayList<AttachedFile> fileList = new ArrayList<>();

        // 클라이언트가 보낸 멀티파트 파일을 FileUpoladHelper Object Storage에 업로드한다.
        for (MultipartFile file : files) {
            if (file.getSize() == 0) {
                continue;
            }
            String filename = fileUpload.upload(this.bucketName, this.uploadDir, file);
            fileList.add(AttachedFile.builder().filePath(filename).build());
        }

        // 업로드한 파일 목록을 세션에 보관한다.
        ArrayList<AttachedFile> oldfileList = (ArrayList<AttachedFile>) session.getAttribute("attachedFiles");
        if (oldfileList != null) {
            oldfileList.addAll(fileList);
            model.addAttribute("fileList", oldfileList);
        } else {
            model.addAttribute("fileList", fileList);
        }

        // 클라이언트에서 이미지 이름을 가지고 <img> 태그를 생성할 수 있도록
        // 업로드한 파일의 이미지 정보를 보낸다.
        return fileList;
    }
}