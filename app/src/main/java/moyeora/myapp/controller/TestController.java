package moyeora.myapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
  @GetMapping("/test/oauth/login")
  public @ResponseBody String testOauthLogin(
      Authentication authentication,
      @AuthenticationPrincipal OAuth2User oauth
  ) {
    System.out.println("=================/test/oauth/login");
    OAuth2User oauth2user = (OAuth2User) authentication.getPrincipal();
    System.out.println("oauth2user: "+oauth2user.getAttributes());
    System.out.println("oauth: "+oauth.getAttributes());
    return "authentication";
  }
}
