package moyeora.myapp.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

  private String isSaveEmail;
  public CustomWebAuthenticationDetails(HttpServletRequest request) {
    super(request);
    isSaveEmail = request.getParameter("saveEmail");
  }
  public String getIsSaveEmail() {
    return isSaveEmail;
  }

}
