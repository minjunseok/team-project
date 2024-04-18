package moyeora.myapp.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Custom-Header")
                .maxAge(3600);


        //추가로 프로젝트를 진행하면서 로컬에서 테스트를 위해 80번포트를 열어
        // 다른 클라이언트의 접속을 시도했을 때 Cors 정책 에러가 발생했었다.
        // 이 문제를 해결하기 위해 추가적인 Config 클래스를 생성.
    }
}
