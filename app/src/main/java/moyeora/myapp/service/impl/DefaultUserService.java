package moyeora.myapp.service.impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

  private final UserDao userDao;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void add(User user) {
    if (user.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    userDao.add(user);
  }

  @Override
  public void save(User user) {
    userDao.save(user);
  }

  @Override
  public List<User> list() {
    return userDao.findAll();
  }

  @Override
  public User get(int no) {
    return userDao.findByNo(no);
  }

  @Override
  public User get(String email) {
    return userDao.findByEmail(email);
  }

  @Override
  public String getEmail(String name, String phone) {
    return userDao.findByNameAndPhone(name,phone);
  }

  @Override
  public void update(User user) {
    userDao.update(user);
  }

  @Override
  public int updatePassword(User user) {
    return userDao.updatePassword(user);
  }

  @Override
  public void delete(int no) {
    userDao.delete(no);
  }

  @Override
  public User findByEmail(String email) {
    return userDao.findByEmail(email);
  }

  @Override
  public User findOAuth2User(String email, String provider) {
    return userDao.findOAuth2User(email, provider);
  }
}