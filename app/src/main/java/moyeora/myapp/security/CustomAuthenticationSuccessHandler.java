package moyeora.myapp.security;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  private final Log log = LogFactory.getLog(CustomAuthenticationSuccessHandler.class);
  private final UserService userService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    log.debug("login Success");

    CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();
    String isSaveEmail = customWebAuthenticationDetails.getIsSaveEmail();
    request.getSession().setAttribute("loginUser", userService.get(((PrincipalDetails) authentication.getPrincipal()).getUser().getNo()));

    Cookie cookie;
    if (isSaveEmail != null && isSaveEmail.equals("on")) {
      cookie = new Cookie("email",
              authentication.getName());
      cookie.setMaxAge(60 * 60 * 24 * 7);
    } else {
      cookie = new Cookie("email", "");
      cookie.setMaxAge(0);
    }
    response.addCookie(cookie);
    response.sendRedirect("/index");
  }
}