package moyeora.myapp.security;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

  private final UserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserDetails userDetails = userDao.getUserDetails(email);
    if(userDetails == null) {
      throw new UsernameNotFoundException("유효하지 않은 로그인 정보입니다.");
    }
    return userDetails;
  }

}
