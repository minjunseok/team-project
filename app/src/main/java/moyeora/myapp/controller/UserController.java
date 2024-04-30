package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import moyeora.myapp.security.util.RedisUtil;
=======
>>>>>>> ccd3fa51ec007f602d2496543dc2d03e8afee64f
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.service.TagService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.service.impl.DefaultMailService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.User;
import moyeora.myapp.vo.UserTag;
import moyeora.myapp.vo.role.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
import javax.mail.MessagingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
=======
>>>>>>> ccd3fa51ec007f602d2496543dc2d03e8afee64f
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
<<<<<<< HEAD
import java.util.Map;
import java.util.Random;
=======
>>>>>>> ccd3fa51ec007f602d2496543dc2d03e8afee64f

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController implements InitializingBean {
  private static final Log log = LogFactory.getLog(UserController.class);
  private final UserService userService;
  private final TagService tagService;
  private final FileUpload fileUpload;
<<<<<<< HEAD
    private final DefaultMailService mailService;
    private final RedisUtil redisUtil;
    private final String uploadDir = "user/";
    private String authId;
  private final UserDao userDao;
  private final HttpSession session;
  @Value("${ncp.storage.bucket}")
  private String bucket;
=======
  private final UserDao userDao;
  private final HttpSession session;
  private final String uploadDir = "user/";
  ;
  @Value("${ncp.storage.bucket}")
  private String bucket;


  @Override
  public void afterPropertiesSet() throws Exception {
>>>>>>> ccd3fa51ec007f602d2496543dc2d03e8afee64f

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

<<<<<<< HEAD
    @PostMapping("add")
    public String add(User user, MultipartFile file) throws Exception{
        System.out.println(user);

        if(file.getSize() > 0){
            String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
            user.setPhoto(filename);
        }
        user.setRole(Role.USER.getKey());
        userService.add(user);

        return "redirect:/index";
    }

    @GetMapping("view")
    public void view(Model model) throws Exception {
        User user = userService.get(1);

        System.out.println("============>");
        System.out.println(user);

        List<UserTag> userTags = user.getTags();
        HashMap<Integer,UserTag> userTagMap = new HashMap<>();
        for (UserTag userTag : userTags) {
            userTagMap.put(userTag.getTagNo(), userTag);
        }

        System.out.println(user);
        model.addAttribute("user",user);
        model.addAttribute("userTagMap",userTagMap);
        model.addAttribute("tags",tagService.findAllTag());
        for(UserTag tag : user.getTags()) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+tag.getTagNo());
        }
    }

@PostMapping("update")
public String update(User user, MultipartFile file) throws Exception {
=======
  @PostMapping("update")
  public String update(User user, MultipartFile file) throws Exception {
>>>>>>> ccd3fa51ec007f602d2496543dc2d03e8afee64f

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
<<<<<<< HEAD
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ user);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ userService);
    return "redirect:/index";
    }

    @PostMapping("passwordUpdate")
    @ResponseBody
    public String update(@RequestBody String password) throws Exception {
        User user = new User();
        user.setPassword(password);
        user.setNo(63);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + password);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ userService);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + userService.passwordUpdate(user));
        userService.passwordUpdate(user);

        return "redirect:index";
    }


    @PostMapping("sendEmail")
    @ResponseBody
    public Object sendEmail(@RequestBody String email, Model model) throws Exception {

        authId = doSend(email, "[moyeora] authentication code", createCode(),
                createAuthId(email), "form");
        model.addAttribute("authId", authId);
        model.addAttribute("status", "sent");
        redisUtil.setDataExpire(authId + "_e", email, 60 * 5L);


        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@" + authId + "_e");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@" + email);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@" + authId);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@" + model.getAttribute("status"));

        Map<String, Object> result = new HashMap<>();
        result.put("authId", authId);
        return result;
    }


    private String doSend(String email, String subject, String code, String authId, String template)
            throws MessagingException {

        mailService.sendEmail(email, subject, code, authId, template);

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$" + email);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$" + authId);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$" + subject);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$" + template);
        redisUtil.setDataExpire(authId, code, 60 * 5L);
        return authId;
    }

    @PostMapping("verifyCode")
    @ResponseBody
    public Object verifyCode(
            String email,
            String code,
            String authId,
            Model model)
            throws Exception {


        String savedCode = (String) redisUtil.getData(authId);

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$" + email);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$" + code);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$" + authId);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$" + model);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$" + savedCode);

        if (savedCode == null) {
            model.addAttribute("status", "savedCode == null");
        } else if (!savedCode.equals(code)) {
            model.addAttribute("status", "savedCode != code");
        } else {
            String userEmail = (String) redisUtil.getData(authId + "_e");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + userEmail);
            model.addAttribute("status", "okok");
            if (redisUtil.existData(authId)) {
                redisUtil.deleteData(authId);
                Map<String, Object> result = new HashMap<>();
                result.put("status", "success");
                return result;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("status", "fail");
        return result;
    }

    private String createAuthId(String email) throws NoSuchAlgorithmException { // 이메일로 항상 유지되는 키값으로 사용할것.
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(email.getBytes());
        md.update(LocalDateTime.now().toString().getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b : md.digest()) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private String createCode() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 12;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
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
=======
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + user);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + userService);
    return "redirect:index";
  }

  @PostMapping("pwdUpdate")
  public String update(User user) throws Exception {

    userService.pwdUpdate(user);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + userService);
    return "redirect:index";
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
>>>>>>> ccd3fa51ec007f602d2496543dc2d03e8afee64f
    log.debug("@@@@@@@@@@==>>>>" + session);
    User loginUser = (User) session.getAttribute("loginUser");
      if (loginUser == null) {
        throw new RuntimeException("로그인 된 사용자 정보를 찾을 수 없습니다.");
      }
    return loginUser;
  }
}

