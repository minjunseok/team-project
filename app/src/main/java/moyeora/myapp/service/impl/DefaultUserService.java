package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
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

    private static final Log log = LogFactory.getLog(DefaultUserService.class);
    private final UserDao userDao;
    private final UserTagDao userTagDao;

    @Override
    public void add(User user) {
        userDao.add(user);
        userTagDao.addAll(user.getTag());
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
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int delete(int no) {
        return userDao.delete(no);
    }


//    @Override
//    public List<UserTag> getUserTag(int userNo) {
//        return null;
//    }
//
//    @Override
//    public int deleteUserTag(int userNo) {
//        return 0;
//    }
}
