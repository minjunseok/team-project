package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.Impl.DefaultMailService;
import moyeora.myapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class MailController {

  private final UserService userService;
  private final DefaultMailService defaultMailService;

  @PostMapping ("/sandEmail")
  public String sandEmail(String email, Model model) {
    if(userService.get(email) == null) {
      model.addAttribute("status","email not found");
    } else {
      String code = defaultMailService.setText(email);
      model.addAttribute("status","sent");
      model.addAttribute("code", code);
    }
    return "auth/findPassword";
  }
}
    