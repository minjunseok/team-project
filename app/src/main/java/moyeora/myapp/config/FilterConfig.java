package moyeora.myapp.config;


import java.util.logging.Filter;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.filter.SchoolAccessFilter;
import moyeora.myapp.vo.School;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ServletComponentScan
@RequiredArgsConstructor
public class FilterConfig {
  private final SchoolDao schoolDao;
  @Bean
  public FilterRegistrationBean<SchoolAccessFilter> schoolAccessFilter() {
    FilterRegistrationBean<SchoolAccessFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(new SchoolAccessFilter(schoolDao));
    return bean;
  }

}
