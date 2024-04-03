package moyeora.myapp.service.Impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

  private final UserDao userDao;

  @Override
  public void add(User user) {
    userDao.add(user);
  }

  @Override
  public List<User> list() {
    return userDao.findAll();
  }

  @Override
  public User get(int no) {
    return userDao.findBy(no);
  }

  @Override
  public User get(String email, String password) {
    return userDao.findByEmailAndPassword(email, password);
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
  public void delete(int no) {
    userDao.delete(no);
  }

}
