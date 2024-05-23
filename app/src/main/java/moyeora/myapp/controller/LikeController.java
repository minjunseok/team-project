package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dto.like.LikeRequestDTO;
import moyeora.myapp.service.LikeService;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.impl.DefaultNotificationService;
import moyeora.myapp.vo.Alert;
import moyeora.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final PostService postService;

    @Autowired
    private final DefaultNotificationService defaultNotificationService;
    @PostMapping("")
    public ResponseEntity<Integer> like(@LoginUser User loginUser, @RequestBody LikeRequestDTO likeRequestDTO) throws Exception {
        likeRequestDTO.setUserNo(loginUser.getNo());

        Alert alert = new Alert();
        alert.setUserNo(postService.getUserNo(likeRequestDTO.getPostNo()));
        alert.setName(loginUser.getNickname());
        alert.setPhoto(loginUser.getPhoto());
        alert.setContent("좋아요를 했습니다");
        alert.setType(3);
        alert.setRedirectPath("mypage/myProfile");

        defaultNotificationService.add(alert);
        return ResponseEntity.status(201).body(likeService.like(likeRequestDTO));
    }
}
