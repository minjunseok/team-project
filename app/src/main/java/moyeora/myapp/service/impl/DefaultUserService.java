package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dao.TagDao;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.dao.UserTagDao;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserDao userDao;
    private final UserTagDao userTagDao;
    private final PasswordEncoder passwordEncoder;
    private final TagDao tagDao;

    @Override
    public void add(User user) {

        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.add(user);
        if (user.getTagNums() != null && user.getTagNums().size() >= 3) {
            for (int tagNum : user.getTagNums()) {

                userTagDao.add(tagNum, user.getNo());
            }
        }
    }

    @Override
    public List<User> list() {
        return userDao.findAll();
    }

    @Override
    public User get(int no) {
        return userDao.findBy(no);
    }


    public User getUserInfo(int no) {
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
        return userDao.findByNameAndPhone(name, phone);
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
    public int passwordUpdate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.passwordUpdate(user);
    }

    @Override
    public int update(User user) {

        userTagDao.deleteAllUserTagNo(user.getNo());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@" + user.getNo());


        if (user.getTagNums() != null && user.getTagNums().size() >= 3) {
            for (int tagNum : user.getTagNums()) {
                userTagDao.add(tagNum, user.getNo());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@" + tagNum);
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@" + user.getNo());

            }
        }

        return userDao.update(user);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public int updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.updatePassword(user);
    }

    @Override
    public User findOAuth2User(String email, String provider) {
        return userDao.findOAuth2User(email, provider);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public String getAddress(int userNo) {
        return userDao.findByNo(userNo).getAddress();
    }
}