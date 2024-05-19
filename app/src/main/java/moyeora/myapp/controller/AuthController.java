package moyeora.myapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.security.config.PasswordEncoderConfig;
import moyeora.myapp.security.util.RedisUtil;
import moyeora.myapp.service.TagService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.service.impl.DefaultMailService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.User;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

  private static final Log log = LogFactory.getLog(AuthController.class);
  private final UserService userService;
  private final DefaultMailService mailService;
  private final RedisUtil redisUtil;
  private final PasswordEncoderConfig passwordEncoderConfig;
  private final TagService tagService;
  private final FileUpload fileUpload;
  private final String uploadDir = "user/";

  @Value("${ncp.storage.bucket}")
  private String bucket;

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

  @GetMapping("signUp")
  public String getSignUpForm(String key, Model model) throws JsonProcessingException {
    User user = redisUtil.getData(key, User.class);
    model.addAttribute("user", user);
    model.addAttribute("tags", tagService.findAllTag());
    return "/user/socialForm";
  }

  @PostMapping("join")
  public String joinTest(User user, Model model, MultipartFile file) throws Exception {
    System.out.println(user.toString());

    if (file.getSize() > 0) {
      String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
      user.setPhoto(filename);
    }

    System.out.println("@@@@@@@@@@@@@@@@" + user.getPhoto());
    userService.add(user);
    System.out.println("@@@@@@@@@@@@@@@@@@@" + userService);
    String key = user.getEmail() + "_" + user.getProvider() + "_" + user.getProviderId();
    if(redisUtil.existData(key)) {
      redisUtil.deleteData(key);
    }
    return "redirect:/";
  }

  @GetMapping("form")
  public void getLoginForm(@CookieValue(required = false) String email,
                           @RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "exception", required = false) String exception,
                           Model model) {
    model.addAttribute("email", email);
    model.addAttribute("error", error);
    model.addAttribute("exception", exception);
  }

  @GetMapping("findEmail")
  public void getFindEmailForm() {
  }

  @PostMapping("getEmail")
  public String getEmail(String name, String phone, Model model) {
    String email = userService.getEmail(name,phone);
    model.addAttribute("email", Objects.requireNonNullElse(email, "none"));
    return "auth/findEmail";
  }

  @GetMapping("findPassword")
  public void getFindPasswordForm(Model model) {
    model.addAttribute("status","request");
  }

  @PostMapping ("sendEmail")
  public String sendEmail(String email, Model model) throws Exception {
    User user = userService.get(email);

    if(user == null || user.getPassword() == null) {
      model.addAttribute("status","email not found");
    } else {
      String authId = doSend(email, "[moyeora] authentication code", createCode(),
              createAuthId(email), "form");
      model.addAttribute("authId", authId);
      model.addAttribute("status","sent");
      redisUtil.setDataExpire(authId+"_e", email,60 * 5L);
    }
    return "auth/findPassword";
  }

  private String doSend(String email, String subject, String code, String authId, String template)
          throws MessagingException {
    mailService.sendEmail(email, subject, code, authId, template);
    redisUtil.setDataExpire(authId, code,60 * 5L);
    return authId;
  }

  @PostMapping("verifyCode")
  public String verifyCode(String email, String code, String authId, Model model)
      throws Exception {
    System.out.println(email+code+authId+model);
    String savedCode = (String) redisUtil.getData(authId);
    if (savedCode == null) {
      model.addAttribute("status","savedCode == null");
    } else if (!savedCode.equals(code)) {
      model.addAttribute("status","savedCode != code");
    } else {
      email = (String)redisUtil.getData(authId+"_e");
      String oldPassword = userService.get(email).getPassword();
      String newPassword = createCode();
      User user = new User();
      user.setEmail(email);
      user.setPassword(newPassword);
      if (userService.updatePassword(user) < 1) {
        throw new Exception("비밀번호 변경 오류");
      }
      try {
        doSend(email, "[moyeora] 임시 비밀번호가 발급되었습니다.", newPassword, createAuthId(email), "password");
      } catch (Exception e) {
        user.setName(oldPassword);
        userService.updatePassword(user);
        throw new Exception("메일 전송 오류");
      }
      model.addAttribute("status","updated");
      if (redisUtil.existData(authId)) {
        redisUtil.deleteData(authId);
      }
      if (redisUtil.existData(authId+"_e")) {
        redisUtil.deleteData(authId+"_e");
      }
    }
    return "auth/findPassword";
  }

  private String createAuthId(String email) throws NoSuchAlgorithmException { // 이메일로 항상 유지되는 키값으로 사용할것.
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(email.getBytes());
    md.update(LocalDateTime.now().toString().getBytes());
    StringBuilder builder = new StringBuilder();
    for (byte b: md.digest()) {
      builder.append(String.format("%02x", b));
    }
    return builder.toString();
  }
}

