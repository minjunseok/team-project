package moyeora.myapp.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

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
