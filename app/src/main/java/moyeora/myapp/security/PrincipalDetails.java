package moyeora.myapp.security;

import lombok.Getter;
import moyeora.myapp.vo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

  @Getter
  private User user;
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
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add((GrantedAuthority) () -> user.getRole());
    return authorities;
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
  public boolean isAccountNonExpired() { // 계정 만료 여부
    return true;
  }

  @Override
  public boolean isAccountNonLocked() { // 계정 잠금 여부
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() { // 패스워드 만료 여부
    return true;
  }

  @Override
  public boolean isEnabled() { // 계정 사용가능 여부
    return true;
  }

  @Override
  public String getName() {
    return user.getName();
  }
}

