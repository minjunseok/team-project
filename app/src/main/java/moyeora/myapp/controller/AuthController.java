package moyeora.myapp.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

  private static final Log log = LogFactory.getLog(AuthController.class);
  private final UserService userService;

  @GetMapping("form")
  public String form(@CookieValue(required = false) String email, Model model) {
    model.addAttribute("email", email);
    return "/templates/auth/form";
  }

  @PostMapping("login")
  public String login(
      String email,
      String password,
      String saveEmail,
      HttpServletResponse response,
      HttpSession session) throws Exception {

    if (saveEmail != null) {
      Cookie cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 7);
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("email", "");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    User user = userService.get(email, password);
    if (user != null) {
      session.setAttribute("loginUser", user);
    }
    return "/templates/auth/login";
  }

  @GetMapping("logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:/index.html";
  }

  @GetMapping("findEmail")
  public String findEmail() throws Exception {
      return "/templates/auth/findEmail";
  }

  @PostMapping("getEmail")
  public String findEmail(String name, String phone, HttpSession session) throws Exception {
    session.setAttribute("email", userService.getEmail(name,phone));
    if (session.getAttribute("email") != null) {
      this.send(session);
    }
    return "/templates/auth/authentication";
  }

  @PostMapping("checkAuthenticationNumber")
  public String authentication(int authenticationNumber, HttpSession session) throws Exception {
    if (session.getAttribute("authenticationNumber").equals(authenticationNumber)) {
      session.setAttribute("ok", true);
    } else {
      session.setAttribute("no", true);
    }
    return "/templates/auth/authentication";
  }

  private void send(HttpSession session) {
    session.setAttribute("authenticationNumber", 111111);
  }
}
