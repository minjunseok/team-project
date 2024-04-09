package moyeora.myapp.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  private final Log log = LogFactory.getLog(CustomAuthenticationFailureHandler.class);

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    log.debug("login Success");

    CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();
    String isSaveEmail = customWebAuthenticationDetails.getIsSaveEmail();

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
    response.sendRedirect("/");
  }
}


