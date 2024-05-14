package moyeora.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class AccessController {

  @GetMapping("")
  public String test() {
    return "/auth/accessDenied";
  }
}
