package moyeora.myapp.filter;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.vo.School;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


@RequiredArgsConstructor
public class SchoolAccessFilter implements Filter {
  private final SchoolDao schoolDao;
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    String paramValue = httpRequest.getParameter("schoolNo");
    System.out.println(paramValue);
    if(paramValue!=null && !paramValue.isEmpty()) {
      School school = schoolDao.findByNo(Integer.parseInt(paramValue));
      if(school.getStop()==1) {
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/index"); // 메인 페이지 경로로 변경
        return;
      }
      if (school.getLimitedAt() != null && school.getLimitedAt().after(Date.valueOf(LocalDate.now()))) {
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/index"); // 메인 페이지 경로로 변경
        return;
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
