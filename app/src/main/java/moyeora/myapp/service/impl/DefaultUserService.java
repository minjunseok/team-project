package moyeora.myapp.service.impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.TagDao;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.dao.UserTagDao;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserDao userDao;
    private final UserTagDao userTagDao;
    private final TagDao tagDao;

  @Override
  public void add(User user) {
    //user.setPwd(passwordEncoder.encode(user.getPwd()));
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

//  @Override
//  public User get(String email, String password) {
//    return userDao.findByEmailAndPassword(email, password);
//  }

  @Override
  public String getEmail(String name, String phone) {
    return userDao.findByNameAndPhone(name,phone);
  }

  @Override
  public void update(User user) {
    //user.setPwd(passwordEncoder.encode(user.getPwd()));
    userDao.update(user);
  }

  @Override
  public void delete(int no) {
    userDao.delete(no);
  }

  @Override
  public List<String> findEmailByUserList(List<Integer> userList) {
    return userDao.findEmailByUserList(userList);
  }

  @Override
  public void downGrade(List<Integer> userList) {
    userDao.downGrade(userList);
  }

}
