package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dto.like.LikeRequestDTO;
import moyeora.myapp.service.LikeService;
import moyeora.myapp.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("")
    public ResponseEntity<Integer> like(@LoginUser User loginUser, @RequestBody LikeRequestDTO likeRequestDTO) {
        likeRequestDTO.setUserNo(loginUser.getNo());
        System.out.println(likeRequestDTO);
        return ResponseEntity.status(201).body(likeService.like(likeRequestDTO));
    }
}
