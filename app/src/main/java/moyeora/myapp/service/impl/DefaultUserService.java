package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

  private final UserDao userDao;

  @Override
  public void add(User user) {
    userDao.add(user);
  }

  @Override
  public List<User> list(int pageNo, int pageSize) {
    return userDao.findAll(pageSize * (pageNo - 1), pageSize);
  }

  @Override
  public User get(int userNo) {
    return userDao.findBy(userNo);
  }

  @Override
  public User get(String email, String pwd) {
    return userDao.findByEmailAndPwd(email, pwd);
  }

  @Override
  public int update(User user) {
    return userDao.update(user);
  }

  @Override
  public int delete(int userNo) {
    return userDao.delete(userNo);
  }

  @Override
  public int countAll() {
    return userDao.countAll();
  }
}
