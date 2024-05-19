package moyeora.myapp.security;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.security.config.PasswordEncoderConfig;
import moyeora.myapp.vo.role.Role;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final PrincipalDetailService principalDetailService;
  private final PasswordEncoder passwordEncoder = new PasswordEncoderConfig().passwordEncoder();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String email = authentication.getName();
    String password = (String)authentication.getCredentials();

    PrincipalDetails principal = (PrincipalDetails) principalDetailService.loadUserByUsername(email);

    if(!passwordEncoder.matches(password, principal.getPassword())) {

      throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(Role.USER.getKey()));


    return new UsernamePasswordAuthenticationToken(principal, password, authorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}