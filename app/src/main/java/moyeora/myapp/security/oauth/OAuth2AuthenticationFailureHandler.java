package moyeora.myapp.security.oauth;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.security.util.RedisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private final Log log = LogFactory.getLog(OAuth2AuthenticationFailureHandler.class);
  private final RedisUtil redisUtil;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    log.debug("OAuth2 login Failed" );
    String errorMessage = exception.getMessage();
    log.debug("exception.getMessage() : " + errorMessage);
    errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
    setDefaultFailureUrl("/auth/form?error=true&exception="+errorMessage);
    super.onAuthenticationFailure(request,response,exception);
  }
}


