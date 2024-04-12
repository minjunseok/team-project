package moyeora.myapp.security.OAuth;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import moyeora.myapp.vo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class PrincipalDetails implements UserDetails, OAuth2User {

  private final User user;
  private Map<String, Object> attributes;

  public PrincipalDetails(User user) {
    this.user = user;
  }
  public PrincipalDetails(User user, Map<String, Object> attributes) {
    this.user = user;
    this.attributes = attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() { // 만료되었는지 확인하는 로직
    return false;
  }

  @Override
  public boolean isAccountNonLocked() { // 계정 잠금되었는지 확인하는 로직
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() { // 패스워드의 만료 여부 반환

    return false;
  }

  @Override
  public boolean isEnabled() { // 계정이 사용 가능한지 확인하는 로직
    return false;
  }

  @Override
  public String getName() {
    return user.getName();
  }
}
