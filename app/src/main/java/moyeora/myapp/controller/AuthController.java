package moyeora.myapp.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.config.MailController;
import moyeora.myapp.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

  private static final Log log = LogFactory.getLog(AuthController.class);
  private final UserService userService;
  private final MailController mailController;

  @GetMapping("form")
  public void getLoginForm(@CookieValue(required = false) String email, Model model) {
    model.addAttribute("email", email);
  }

  @GetMapping("findEmail")
  public void getFindEmailForm() throws Exception {
  }

  @PostMapping("getEmail")
  public String getEmail(String name, String phone, Model model) throws Exception {
    String email = userService.getEmail(name,phone);
    model.addAttribute("email", Objects.requireNonNullElse(email, "none"));
    return "auth/findEmail";
  }

  @GetMapping("findPassword")
  public void getFindPasswordForm(Model model) throws Exception {
    model.addAttribute("status","request");
  }

  @PostMapping("checkCode")
  public String checkCode(String code, String authCode, Model model) throws Exception {
    if (code.equals(authCode)) {
      model.addAttribute("status", "success");
    } else {
      model.addAttribute("status", "fail");
    }
    return "auth/findPassword";
  }

  @PostMapping("getPassword")
  public void getPassword() {}
}