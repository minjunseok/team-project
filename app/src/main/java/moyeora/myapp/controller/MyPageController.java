package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dto.profile.*;
import moyeora.myapp.service.MyPageService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.impl.DefaultNotificationService;
import moyeora.myapp.vo.Alert;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/mypage")
public class MyPageController {
  private final SchoolService schoolService;
  private final MyPageService myPageService;

  @Autowired
  private final DefaultNotificationService defaultNotificationService;
  final static Log log = LogFactory.getLog(MyPageController.class);

  @GetMapping("mypost")
  public void myPost(Model model) {
    model.addAttribute("newPosts",myPageService.findNewPost(1));
    model.addAttribute("schools",schoolService.findByUserNo(1).getSchools());
  }

  @GetMapping("myProfile")
  public void profile(Model model, @LoginUser User loginUser) {
    //model.addAttribute("newPosts",myPageService.findNewPost(loginUser.getNo()));
    SchoolUser schoolUser = schoolService.findByUserNo(loginUser.getNo());
    if(schoolUser!=null) {
      model.addAttribute("schools", schoolService.findByUserNo(loginUser.getNo()).getSchools());
      model.addAttribute("userNo", loginUser.getNo());
      model.addAttribute("loginUser", loginUser);
    }
  }

  @GetMapping("recommenduser")
  @ResponseBody
  public ResponseEntity<List<User>> recommendUser() {
    return ResponseEntity.ok(null);
  }

  @GetMapping("profile")
  @ResponseBody
  public ResponseEntity<ProfileResponseDTO> profile(@LoginUser User loginUser, @RequestParam(defaultValue = "0") int userNo, int page) {
    if (userNo == 0) {
      userNo = loginUser.getNo();
    }
    return ResponseEntity.status(200).body(myPageService.getProfile(userNo, page));
  }

  @GetMapping("test")
  public void test() {

  }

  @GetMapping("modal/profile")
  public void modal(@LoginUser User loginUser ,Model model) {
    model.addAttribute("userNo", loginUser.getNo());

  }

  @GetMapping("followList")
  @ResponseBody
  public ResponseEntity<List<FollowListResponseDTO>> followList(@LoginUser User loginUser, FollowListRequestDTO followListRequestDTO) throws Exception {
    if(loginUser!=null && loginUser.getNo() > 0) {
      System.out.println(loginUser.getNo()+"ss");
      followListRequestDTO.setClickUserNo(loginUser.getNo());
    }
    return ResponseEntity.status(200).body(myPageService.followList(followListRequestDTO));
  }

  @PostMapping("addFollow")
  @ResponseBody
  public ResponseEntity<Integer> addFollow(@LoginUser User loginUser, @RequestBody FollowRequestDTO followRequestDTO) throws Exception {
    if(loginUser.getNo()==followRequestDTO.getFollowerUserNo()) {
      return ResponseEntity.status(400).build();
    }
    Alert alert = new Alert();
    alert.setUserNo(followRequestDTO.getFollowerUserNo());
    alert.setName(loginUser.getNickname());
    alert.setPhoto(loginUser.getPhoto());
    alert.setContent("팔로우 하였습니다");
    alert.setType(1);
    alert.setRedirectPath("/mypage/myProfile");
    defaultNotificationService.add(alert);
    followRequestDTO.setFollowingUserNo(loginUser.getNo());

    return ResponseEntity.status(201).body(myPageService.addFollow(followRequestDTO));
  }

  @GetMapping("checkFollow")
  @ResponseBody
  public ResponseEntity<Integer> checkFollow(@LoginUser User loginUser, FollowRequestDTO followRequestDTO) {
    followRequestDTO.setFollowingUserNo(loginUser.getNo());
    return ResponseEntity.status(200).body(myPageService.checkFollow(followRequestDTO));
  }

  @GetMapping("likepost")
  @ResponseBody
  public ResponseEntity<List<ProfileResponse2DTO>> getLikePost(@LoginUser User loginUser, int page) {
    return ResponseEntity.status(200).body(myPageService.getLikePost(loginUser.getNo(), page));
  }

  @GetMapping("followerpost")
  @ResponseBody
  public ResponseEntity<List<ProfileResponse2DTO>> getLFollowerPost(@LoginUser User loginUser, int page) {
    return ResponseEntity.status(200).body(myPageService.getFollowerPost(loginUser.getNo(), page));
  }

  @GetMapping("schoolpost")
  @ResponseBody
  public ResponseEntity<List<ProfileResponse2DTO>> getSchoolPost(@LoginUser User loginUser, int page) {
    return ResponseEntity.status(200).body(myPageService.getSchoolPost(loginUser.getNo(), page));
  }
}
