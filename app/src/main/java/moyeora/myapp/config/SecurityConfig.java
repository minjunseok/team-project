package moyeora.myapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;
  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;
  @Autowired
  private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

  // Web Ignore
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
      .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
    httpSecurity
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests((authorize) -> authorize
          .anyRequest().permitAll()
//            .antMatchers("/","/index","/home","/auth/**").permitAll()
//            .anyRequest().authenticated()
      )
      .exceptionHandling((exceptionConfig) ->
        exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
      )
      .formLogin(form -> form
        .loginPage("/auth/form")
        .usernameParameter("email")
        .passwordParameter("password")
        .authenticationDetailsSource(authenticationDetailsSource)
        .loginProcessingUrl("/auth/login")
        .successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        .permitAll()
      )
      .logout((logout) -> logout
        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true)
      );
    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  public final AuthenticationEntryPoint unauthorizedEntryPoint =
    (request, response, authException) -> {
      ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      String json = new ObjectMapper().writeValueAsString(fail);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      PrintWriter writer = response.getWriter();
      writer.write(json);
      writer.flush();
    };

  public  final AccessDeniedHandler accessDeniedHandler =
    (request, response, accessDeniedException) -> {
      ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
      response.setStatus(HttpStatus.FORBIDDEN.value());
      String json = new ObjectMapper().writeValueAsString(fail);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      PrintWriter writer = response.getWriter();
      writer.write(json);
      writer.flush();
    };

  @Getter
  @RequiredArgsConstructor
  public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
  }

}