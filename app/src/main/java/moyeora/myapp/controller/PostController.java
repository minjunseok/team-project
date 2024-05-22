package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dto.AjaxResponse;
import moyeora.myapp.security.PrincipalDetails;
import moyeora.myapp.service.CommentService;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
//@SessionAttributes("attachedFiles")
public class PostController {

    //  private static final Log log = LogFactory.getLog(PostController.class);
    private final PostService postService;
    private final FileUpload fileUpload;
    private final SchoolService schoolService;
    private final SchoolUserService schoolUserService;
    private final CommentService commentService;
    private String uploadDir = "post/";
    private static final Log log = LogFactory.getLog(PostController.class);


    @Value("${ncp.storage.bucket}")
    private String bucketName;


    @GetMapping("form")
    public void form() throws Exception {

    }


//스쿨 공지를 다루는 메서드
    @PostMapping("fix")
    @ResponseBody
    public AjaxResponse fixedPost(Post post,
                                  HttpSession httpSession,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails,
                                  @LoginUser User loginUser) {


        if (loginUser == null) {
            return AjaxResponse.builder().status("error").message("로그인이 필요합니다.").build();
        }

        int userNo = loginUser.getNo();
        int schoolNo = postService.findByPostSchoolNo(post.getNo());


        log.debug("schoolNo + @@@@@@@@@@@@@@@@@@@" + schoolNo);
        log.debug("userNo + @@@@@@@@@@@@@@@@@@@@@" + userNo);
        log.debug("sessionNo + @@@@@@@@@@@@@@@@@@@@@" + loginUser.getNo());


        int levelNo = schoolUserService.findLevel(schoolNo, userNo);

        log.debug("levelNo + @@@@@@@@@@@@@@@@@@@@@@@@" + levelNo);


        if (levelNo == 3 || levelNo == 4) {


            try {


                // 메서드를 호출하여 글 고정을 하고 결과를 받아옵니다.
                postService.fixedPost(post);

                // 성공적으로 고정을 등록했을 경우 응답을 생성합니다.
                return AjaxResponse.builder().status("success").build();

            } catch (Exception e) {
                // 글 고정에 실패한 경우 예외를 처리하고 에러 응답을 생성합니다.

                return AjaxResponse.builder().status("error").message("게시글 고정에 실패하였습니다.").build();
            }

        } else {

            return AjaxResponse.builder().status("error").message("권한이 없습니다.").build();

        }

    }


    @PostMapping("noticeFixedCancel")
    @ResponseBody
    public AjaxResponse noticeFixedCancel(Post post,
                                          @LoginUser User loginUser) {


        int userNo = loginUser.getNo();
        int schoolNo = postService.findByPostSchoolNo(post.getNo());


        log.debug("schoolNo + @@@@@@@@@@@@@@@@@@@" + schoolNo);
        log.debug("userNo + @@@@@@@@@@@@@@@@@@@@@" + userNo);
        log.debug("sessionNo + @@@@@@@@@@@@@@@@@@@@@" + loginUser.getNo());


        int levelNo = schoolUserService.findLevel(schoolNo, userNo);

        log.debug("levelNo + @@@@@@@@@@@@@@@@@@@@@@@@" + levelNo);

        if (levelNo == 3 || levelNo == 4) {


            try {

                //메서드를 호출하여 글 고정 취소를 진행하고 결과를 받아옵니다.
                postService.noticeFixedCancel(post);

                //
                return AjaxResponse.builder().status("success").build();

            } catch (Exception e) {
                // 고정 취소에 실패한 경우 예외를 처리하고 에러 응답을 생성합니다.
                return AjaxResponse.builder().status("error").message("게시글 고정해제 실패").build();
            }
        } else {

            return AjaxResponse.builder().status("error").message("권한이 없습니다.").build();
        }
    }


    @PostMapping("fixCancel")
    @ResponseBody
    public AjaxResponse fixedCancel(Post post,
                                    @LoginUser User loginUser) {


        if (loginUser == null) {
            return AjaxResponse.builder().status("error").message("로그인이 필요합니다.").build();
        }

        int userNo = loginUser.getNo();
        int schoolNo = postService.findByPostSchoolNo(post.getNo());


        log.debug("schoolNo + @@@@@@@@@@@@@@@@@@@" + schoolNo);
        log.debug("userNo + @@@@@@@@@@@@@@@@@@@@@" + userNo);
        log.debug("sessionNo + @@@@@@@@@@@@@@@@@@@@@" + loginUser.getNo());


        int levelNo = schoolUserService.findLevel(schoolNo, userNo);

        log.debug("levelNo + @@@@@@@@@@@@@@@@@@@@@@@@" + levelNo);

        if (levelNo == 3 || levelNo == 4) {


            try {

                //메서드를 호출하여 글 고정 취소를 진행하고 결과를 받아옵니다.
                postService.fixedCancel(post);

                //
                return AjaxResponse.builder().status("success").build();

            } catch (Exception e) {
                // 고정 취소에 실패한 경우 예외를 처리하고 에러 응답을 생성합니다.
                return AjaxResponse.builder().status("error").message("게시글 고정해제 실패").build();
            }
        } else {

            return AjaxResponse.builder().status("error").message("권한이 없습니다.").build();
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

        Map<String, Object> result = new HashMap<>();
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
            String filename = fileUpload.upload(this.bucketName, this.uploadDir, file);
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
            @LoginUser User loginUser,
            @RequestParam int schoolNo,
            Post post,
            HttpSession session,
            SessionStatus sessionStatus) throws Exception {


        int userNo = loginUser.getNo();
        // 게시글 등록할 때 삽입한 이미지 목록을 세션에서 가져온다.
        List<AttachedFile> attachedFiles = (List<AttachedFile>) session.getAttribute("attachedFiles");
        schoolNo = (Integer) session.getAttribute("schoolNo");

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
        post.setUserNo(userNo);
        log.debug("@@@@@@@@@@@@@@ userNo 주입성공 = " + userNo);
        post.setSchoolNo(schoolNo);
        log.debug("@@@@@@@@@@@@@ schoolNo 주입성공 = " + schoolNo);


        // 나머지 처리 코드
        log.debug("@@@@@@@ 주입만 완료된 post 객체 ===>>" + post);
        postService.add(post);


        return "redirect:list?schoolNo=" + schoolNo;
    }


    @GetMapping("list")
    public void list(Model model,
                     @RequestParam("schoolNo") int schoolNo,
                     @AuthenticationPrincipal PrincipalDetails principalDetails,
                     @LoginUser User loginUser) {

        School school = schoolService.get(schoolNo); // schoolNo로 학교 정보를 가져오는 메서드 호출
        String schoolPhotoUrl = "https://qryyl2ox2742.edge.naverncp.com/yNmhwcnzfw/school/" + school.getPhoto();
        String schoolName = school.getName();

        if (loginUser != null) {
            model.addAttribute("sender", loginUser);
            model.addAttribute("loginUser", loginUser);
            int memberCheck = schoolUserService.findByMemberCheck(schoolNo, loginUser.getNo());

            if (memberCheck == 1) {

                int userNo = loginUser.getNo();

                // 회원이고 해당 학교의 회원인 경우
                log.debug("@@@@@@@@@@@@@@@@ 회원이니까 모델을 전부다 넘긴다");
                int accessLevel = schoolUserService.findLevel(schoolNo, userNo);

                model.addAttribute("accessLevel", accessLevel);
                model.addAttribute("school", school);

            }
        }

        // 로그인 상태와 관계없이 공통으로 처리해야 할 부분
        List<Post> posts = postService.findBySchoolPostList(schoolNo);
        Post post = postService.findByFixList(schoolNo);
        model.addAttribute("schoolNo", schoolNo);
        model.addAttribute("postlist", posts);
        model.addAttribute("schoolUsers", schoolUserService.findBySchoolUserList(schoolNo));
        model.addAttribute("fixlist", post);
        model.addAttribute("school", school);
        model.addAttribute("schoolPhotoUrl", schoolPhotoUrl);
        model.addAttribute("schoolName", schoolName);
    }


    @GetMapping("view/{lNo}")
    @ResponseBody
    public Object findByPost(int no, @PathVariable String lNo) throws Exception {

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

        Map<String, Object> result = new HashMap<>();
        result.put("comments", comments);
        result.put("files", attachedFiles);
        result.put("post", post);
        return result;
    }


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
                log.debug("@@@@@@@@@@@@@@@@ 회원이니까 모델을 전부다 넘긴다");
                int accessLevel = schoolUserService.findLevel(schoolNo, userNo);
                model.addAttribute("sender", loginUser);
                model.addAttribute("accessLevel", accessLevel);
                model.addAttribute("loginUser", loginUser);
            } else {
                // 회원이지만 해당 학교의 회원이 아닌 경우
                log.debug("@@@@@@@@@@@@@@@@ 넌 비회원이구나");
            }
        } else {
            // 비회원인 경우
            log.debug("@@@@@@@@@@@@@@@@ 넌 로그인을 아예 안 했구나");
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

        log.debug("@@@@@@@" + schoolNo + keyword);
        return "post/list";

    }


    @PostMapping("update")
    public String update(
            Post post,
            Integer no,
            Integer schoolNo,
            MultipartFile[] files,
            HttpSession session) throws Exception {


        List<AttachedFile> attachedFiles = (List<AttachedFile>) session.getAttribute("attachedFiles");
        System.out.println("ASDASDaSdasdasdaasdasdasdasdsasdasdasd");
        System.out.println(post);

        log.debug("@@@@@@@@@@@@ 세션에서 가져온 schoolNo = " + session.getAttribute("schoolNo"));

        schoolNo = (Integer) session.getAttribute("schoolNo");

        log.debug(" @@@@@@@@@@@@@@ 형변환 성공한 schoolNo = " + schoolNo);

        log.debug("@@@@@@@@@@@@ 세션에서 가져온 postNo = " + session.getAttribute("postNo"));

        no = (Integer) session.getAttribute("postNo");

        log.debug(" @@@@@@@@@@@@@@ 형변환 성공한 postNo = " + no);


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
        log.debug("변경된 서머노트 내용이 정상적으로 꽂혔을까요? = " + post);

        post.setSchoolNo(schoolNo);

        log.debug("가져온 postNo가 정상적으로 꽂혔을까요? = " + schoolNo);
        post.setNo(no);

        log.debug("가져온 postNo이 정상적으로 꽂혔을까요? = " + no);

        postService.update(post);

        return "redirect:list?schoolNo=" + post.getSchoolNo();

    }

    @ResponseBody
    @GetMapping("delete")
    public String delete(
            Post post,
            int no,
            int schoolNo,
            HttpSession session) throws Exception {


        List<AttachedFile> files = postService.getAttachedFiles(no);


        log.debug("@@@@@@@@@@@@@@@@@@@ 게시글번호" + post.getNo());
        log.debug("@@@@@@@@@@@@@@@@@@@ 게시글번호" + post.getSchoolNo());

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


        AttachedFile fileList = postService.getAttachedFile(no);
        if (fileList == null) {
            throw new Exception("첨부파일 번호가 유효하지 않습니다.");
        }


        postService.deleteAttachedFile(no);


        return "redirect:list?schoolNo=" + post.getSchoolNo();
    }


    @PostMapping("file/upload")
    @ResponseBody
    public AjaxResponse fileUpload(
            MultipartFile[] files,
            HttpSession session,
            @LoginUser User loginUser,
            @RequestParam int schoolNo,
            @RequestParam(value = "postNo", required = false) Integer no,
            Model model) throws Exception {

//         FileUpoladHelper Object Storage에 저장한 파일의 이미지 이름을 보관할 컬렉션을 준비한다.
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
        session.setAttribute("schoolNo", schoolNo);

        log.debug("schoolNo가 session 에 담겼을까요" + session.getAttribute("schoolNo"));

        session.setAttribute("postNo", no);

        log.debug("postNo가 session 에 담겼을까요" + session.getAttribute("postNo"));


        if (oldfileList != null) {
            oldfileList.addAll(fileList);
            model.addAttribute("fileList", oldfileList);
        } else {
            model.addAttribute("fileList", fileList);
        }

        // 클라이언트에서 이미지 이름을 가지고 <img> 태그를 생성할 수 있도록
        // 업로드한 파일의 이미지 정보를 보낸다.
        return AjaxResponse.builder().status("success").data(fileList).build();
    }


}