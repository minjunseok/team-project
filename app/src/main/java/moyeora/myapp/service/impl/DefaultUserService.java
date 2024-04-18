package moyeora.myapp.service.impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.dao.TagDao;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.dao.UserTagDao;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import moyeora.myapp.vo.UserTag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserDao userDao;
    private final UserTagDao userTagDao;
    private final SchoolUserDao schoolUserDao;
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
        return userDao.findBy(no);
    }

    @Override
    public int findUserGrade(int grade) {
        return userDao.findUserGrade(grade);
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

  @Override
  public int pwdUpdate(User user) {
      return userDao.pwdUpdate(user);
  }

  @Override
  public int update(User user) {

          userTagDao.deleteAllUserTagNo(user.getNo());
      System.out.println("@@@@@@@@@@@@@@@@@@@@@@@"+user.getNo());



      if(user.getTagNums() != null && user.getTagNums().size() >=3) {
          for(int tagNum : user.getTagNums()) {
              userTagDao.add(tagNum, user.getNo());
              System.out.println("@@@@@@@@@@@@@@@@@@@@@@@"+tagNum);
              System.out.println("@@@@@@@@@@@@@@@@@@@@@@@"+user.getNo());

          }
      }

      return userDao.update(user);
  }

}