package moyeora.myapp.security.oauth;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.security.PrincipalDetails;
import moyeora.myapp.security.oauth.userInfo.OAuth2UserInfo;
import moyeora.myapp.security.util.RedisUtil;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final Log log = LogFactory.getLog(OAuth2AuthenticationSuccessHandler.class);

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    log.debug("OAuth2 login Success");
    User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
    String key = user.getEmail() + "_" + user.getProvider() + "_" + user.getProviderId();
    if(true) {
      String redirectUrl = UriComponentsBuilder
          .fromUriString("http://localhost:8080/auth/test")
          .queryParam("key",key)
          .build()
          .encode(StandardCharsets.UTF_8)
          .toString();
      getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    } else {
      response.sendRedirect("/");
    }
  }
}


