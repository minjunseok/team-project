package moyeora.myapp.security.config;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.filter.AdminAccessFilter;
import moyeora.myapp.filter.SchoolAccessFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ServletComponentScan
@RequiredArgsConstructor
public class FilterConfig {
  private final SchoolDao schoolDao;
  private final SchoolUserDao schoolUserDao;
  @Bean
  public FilterRegistrationBean<SchoolAccessFilter> schoolAccessFilter() {
    FilterRegistrationBean<SchoolAccessFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(new SchoolAccessFilter(schoolDao));
    bean.addUrlPatterns("/*");
    return bean;
  }

  @Bean
  public FilterRegistrationBean<AdminAccessFilter> adminAccessFilter() {
    FilterRegistrationBean<AdminAccessFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(new AdminAccessFilter());
    bean.addUrlPatterns("/admin/*");
    return bean;
  }


}
