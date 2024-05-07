package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.security.util.RedisUtil;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.service.TagService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.User;
import moyeora.myapp.vo.UserTag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController implements InitializingBean {
    private static final Log log = LogFactory.getLog(UserController.class);
    private final UserService userService;
    private final TagService tagService;
    private final FileUpload fileUpload;
    private final String uploadDir = "user/";
    private String authId;
  private final UserDao userDao;
  private final HttpSession session;
  @Value("${ncp.storage.bucket}")
  private String bucket;
  
  @Override
  public void afterPropertiesSet() throws Exception {

    log.debug(String.format("uploadDir: %s", this.uploadDir));
  }

  @GetMapping("form")
  public void form(Model model) throws Exception {

    model.addAttribute("tags", tagService.findAllTag());
  }

  @PostMapping("add")
  public String add(User user, MultipartFile file) throws Exception {


    if (file.getSize() > 0) {
      String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
      user.setPhoto(filename);
    }
    userService.add(user);

    return "redirect:index";
  }

  @GetMapping("view")
  public void view(Model model) throws Exception {
    User user = userService.get(42);
    List<UserTag> userTags = user.getTags();
    HashMap<Integer, UserTag> userTagMap = new HashMap<>();
    for (UserTag userTag : userTags) {
      userTagMap.put(userTag.getTagNo(), userTag);
    }

    System.out.println(user);
    model.addAttribute("user", user);
    model.addAttribute("userTagMap", userTagMap);
    model.addAttribute("tags", tagService.findAllTag());
    for (UserTag tag : user.getTags()) {
      System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + tag.getTagNo());
    }
  }

  @PostMapping("update")
  public String update(User user, MultipartFile file) throws Exception {

    User old = userService.get(1);
    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$" + old);
    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$" + old.getNo());
    if (old == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }
    user.setNo(old.getNo());

    user.setCreatedAt(old.getCreatedAt());


    if (file.getSize() > 0) {
      String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
      user.setPhoto(filename);
      fileUpload.delete(this.bucket, this.uploadDir, old.getPhoto());
    } else {
      user.setPhoto(old.getPhoto());
    }

        userService.update(user);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + user);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ userService);
        return "redirect:index";
    }

    @PostMapping("pwdUpdate")
    public String update(User user) throws Exception {
        userService.pwdUpdate(user);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ userService);
        return "redirect:index";
    }


    @GetMapping("userProfile")
    public String userProfile(int no, Model model) {
        // 사용자 서비스를 사용하여 사용자 정보를 가져옵니다.
        User user = userService.get(no);
        // 모델에 사용자 정보를 추가합니다.
        model.addAttribute("user", user);
        // userProfile.html로 이동합니다.
        return "header";

    }

  @PostMapping("/userNo")
  @ResponseBody
  public User getUserNo(HttpSession session) {

    log.debug("@@@@@@@@@@==>" + session.getAttribute("SecurityContextImpl"));
    Enumeration<?> attrName = session.getAttributeNames();
    while (attrName.hasMoreElements()) {
      String attr = (String) attrName.nextElement();
      System.out.println("@@@@@@@@@@@@@=>"+session.getAttribute(attr));
    }

    log.debug("@@@@@@@@@@==>>>>" + session);
    User loginUser = (User) session.getAttribute("loginUser");
      if (loginUser == null) {
        throw new RuntimeException("로그인 된 사용자 정보를 찾을 수 없습니다.");
      }
    return loginUser;
  }
}

