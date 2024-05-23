package moyeora.myapp.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminAccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        System.out.println("a");
//        User user = (User) httpRequest.getSession().getAttribute("loginUser");
//        if (user.getAuth() < 3 || user == null) {
//            httpServletResponse.sendRedirect(httpRequest.getContextPath() + "/index"); // 메인 페이지 경로로 변경
//        }
        chain.doFilter(request, response);
    }
}
