package moyeora.myapp.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  // Spring Boot에서는 WebMvcConfigurer 인터페이스를 구현하여
  // addFormatters 메서드에서 변환기를 등록할 수 있습니다.
  //이제 스프링은 MultipartFile 객체를 AttachedFile 객체로 변환하여 엔티티 객체에 할당할 수 있게 됩니다.

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new MultipartFileToAttachedFileConverter());
    }
}