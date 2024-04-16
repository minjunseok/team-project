package moyeora.myapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.Impl.DefaultMailService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.security.util.RedisUtil;
import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;
  private final DefaultMailService mailService;
  private final RedisUtil redisUtil;

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

  @GetMapping("test")
  public void getTestForm(String key, Model model) throws JsonProcessingException {
    User user = redisUtil.getData(key, User.class);
    model.addAttribute("user", user);
  }

  @PostMapping("join")
  public String joinTest(User user, Model model) {
    model.addAttribute("result", userService.save(user));
    return "/auth/test";
  }

  @GetMapping("form")
  public void getLoginForm(@CookieValue(required = false) String email, Model model) {
    model.addAttribute("email", email);
  }

  @GetMapping("findEmail")
  public void getFindEmailForm() {
  }

  @PostMapping("getEmail")
  public String getEmailByNameAndPhone(String name, String phone, Model model) {
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
    if(userService.get(email) == null) { // 다른 기능이랑 공유하려면 이부분 수정해야함
      model.addAttribute("status","email not found");
    } else {
      String authId = createAuthId(email);
      String code = createCode();
      mailService.sendEmail(email, code, authId);
      model.addAttribute("status","sent");
      model.addAttribute("authId", authId);
      redisUtil.setDataExpire(authId, code,60 * 5L);
    }
    return "/auth/findPassword";
  }

  @PostMapping("verifyCode")
  public String verifyCode(String email, String code, String authId, Model model) throws NoSuchAlgorithmException {
    String savedCode = (String) redisUtil.getData(authId);
    if (savedCode == null) {
      model.addAttribute("status","savedCode == null");
    } else if (!savedCode.equals(code)) {
      model.addAttribute("status","savedCode != code");
    } else {
      model.addAttribute("status","success");
    }
    return "/auth/findPassword";
  }

  private String createAuthId(String email) throws NoSuchAlgorithmException { // 이메일로 항상 유지되는 키값으로 사용할것.
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(email.getBytes());
    md.update(LocalDateTime.now().toString().getBytes()); // 여기 확인후 수정 필요해보임
    StringBuilder builder = new StringBuilder();
    for (byte b: md.digest()) {
      builder.append(String.format("%02x", b));
    }
    return builder.toString();
  }
}
