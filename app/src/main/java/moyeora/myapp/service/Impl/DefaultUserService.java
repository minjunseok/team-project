package moyeora.myapp.service.Impl;


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
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userDao.update(user);
  }

  @Override
  public void delete(int no) {
    userDao.delete(no);
  }

  @Override
  public User findByUsername(String username) {
    return userDao.findByUsername(username);
  }

  @Override
  public User findByEmail(String email) {
    return userDao.findByEmail(email);
  }

}
