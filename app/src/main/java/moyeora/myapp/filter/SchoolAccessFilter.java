package moyeora.myapp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.vo.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@WebFilter("/*")
@RequiredArgsConstructor
public class SchoolAccessFilter implements Filter {
  private final SchoolDao schoolDao;
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String paramValue = httpRequest.getParameter("schoolNo");
    if(paramValue!=null && !paramValue.isEmpty()) {
      School school = schoolDao.findByNo(Integer.parseInt(paramValue));
      if(school.getStop()==1) {
        throw new ServletException();
      }
    }
      chain.doFilter(request,response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void destroy() {
  }
}
