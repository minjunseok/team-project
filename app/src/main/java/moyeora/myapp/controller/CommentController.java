package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dto.AjaxResponse;
import moyeora.myapp.security.PrincipalDetails;
import moyeora.myapp.service.CommentService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.vo.Comment;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.repository.query.ExtensionAwareQueryMethodEvaluationContextProvider;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {


    private final CommentService commentService;
    private final SchoolUserService schoolUserService;
    private static final Log log = LogFactory.getLog(PostController.class);

//    public CommentController(CommentService commentService, SchoolUserService schoolUserService) {
//        this.commentService = commentService;
//        this.schoolUserService = schoolUserService;
//    }

    @ResponseBody
    @PostMapping("addComment")
    public AjaxResponse commentAdd(
            @RequestBody Comment comment,
            @LoginUser User loginUser) throws Exception {



        //사전에 세션의 로그인 여부를 체크한 후 ajax 쪽으로 결과 dto 리턴
        if (loginUser == null) {
            return AjaxResponse.builder().status("error").message("로그인이 필요합니다.").build();
        }

        int levelNo = schoolUserService.findLevel(comment.getSchoolNo(), loginUser.getNo());
        log.debug("@@@@@@@@@@@@@@@@@ levelNo = " + levelNo);

        // 조건이 충족되지 않았을 때
        if (levelNo == 1 || levelNo == 5) {
            return AjaxResponse.builder().status("error").message("해당 스쿨 회원이 아닙니다").build();
        }

        // 조건이 충족되었을 때
        try {

            log.debug("@@@@@@@@@@@@@@@@@@ comment 데이터 주입전" + comment);

            log.debug("@@@@@@@@@@@@@@@@@@@" + loginUser.getNo());
            int userNo = loginUser.getNo();
            log.debug("@@@@@@@@@@@@@@ userNo=" + userNo);

            comment.setCreatedAt(new Date());
            comment.setUserNo(userNo);

            log.debug("@@@@@@@@@@@@@@@@@@ comment = " + comment);

            commentService.addComment(comment);

            log.debug("@@@@@@@@@@@@@@@@@@ comment 데이터 주입후 = " + comment);

            // 성공적으로 댓글을 등록했을 경우 생성합니다.
            return AjaxResponse.builder().status("댓글 등록 성공").build();

        } catch (Exception e) {
            // 글 댓글 등록에 실패한 경우 예외를 처리하고 에러 응답을 생성합니다.
            return AjaxResponse.builder().status("error").message("Failed to add comment").build();
        }
    }

    @ResponseBody
    @PostMapping("updateComment")
    public AjaxResponse commentUpdate(
            @RequestBody Comment comment,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @LoginUser User loginUser) throws Exception {

        if (loginUser == null) {
            return AjaxResponse.builder().status("error").message("로그인이 필요합니다.").build();
        }


        int commentUserNo = comment.getUserNo();

        log.debug("@@@@@@@@@@@@@@@@@@ commentUserNo = " + commentUserNo);

        int userNo = loginUser.getNo();

        log.debug("@@@@@@@@@@@@@@@@@@ userNo = " + userNo);

        if (userNo == commentUserNo) {

            try {


                String content = comment.getContent();

                int commentNo = comment.getCommentNo();

                log.debug("@@@@@@@@@@@@@@@@@@ comment 데이터 주입전" + comment);



                comment.setContent(content);

                log.debug("@@@@@@@@@@@@@@@@@@ setContent 데이터 주입내용" + content);


                comment.setCommentNo(commentNo);

                log.debug("@@@@@@@@@@@@@@@@@@ setCommentNo 데이터 주입내용" + commentNo);


                commentService.update(comment);


                log.debug("@@@@@@@@@@@@@@@@@@ comment 데이터 주입후" + comment);

                return AjaxResponse.builder().status("success").build();


            } catch (Exception e) {

                // 댓글 수정 실패 시 에러 응답을 생성합니다.
                return AjaxResponse.builder().status("error").message("댓글 수정 실패").build();
            }

        } else {

            return AjaxResponse.builder().status("error").message("권한이 없습니다.").build();

        }
    }




    @ResponseBody
    @PostMapping("deleteComment")
    public AjaxResponse commentDelete(
            @RequestBody Comment comment,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @LoginUser User loginUser) throws Exception {


        if (loginUser == null) {
            return AjaxResponse.builder().status("error").message("로그인이 필요합니다.").build();
        }


        int levelNo = schoolUserService.findLevel(comment.getSchoolNo(), loginUser.getNo());


        log.debug("@@@@@@@@@@@@@@@@ levelNo" + levelNo);

        int commentUserNo = comment.getUserNo();

        log.debug("@@@@@@@@@@@@@@@@@@ commentUserNo = " + commentUserNo);

        int userNo = loginUser.getNo();

        log.debug("@@@@@@@@@@@@@@@@@@ userNo = " + userNo);

//        if ((userNo == commentUserNo) || (levelNo == 3 || levelNo == 4)) {
            if (userNo == commentUserNo) {

            try {


                int commentNo = comment.getCommentNo();
//
//                log.debug("@@@@@@@@@@@@@@@@@@ comment 데이터 주입전" + comment);
//
//
//
//                comment.setCommentNo(commentNo);

                log.debug("@@@@@@@@@@@@@@@@@@ 삭제할 댓글 번호" + commentNo);


                commentService.delete(commentNo);


                return AjaxResponse.builder().status("success").build();


            } catch (Exception e) {

                // 댓글 수정 실패 시 에러 응답을 생성합니다.
                return AjaxResponse.builder().status("error").message("댓글 삭제 실패").build();
            }

        } else {

            return AjaxResponse.builder().status("error").message("권한이 없습니다.").build();

        }
    }
}






