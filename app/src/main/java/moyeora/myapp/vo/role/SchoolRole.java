package moyeora.myapp.vo.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SchoolRole {
  ZERO("ROLE_ZERO","ZERO"),
  USER("ROLE_USER","USER"),
  ADMIN("ROLE_ADMIN","ADMIN");

  private final String key;
  private final String title;
}
