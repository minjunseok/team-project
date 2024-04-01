package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private static final Log log = LogFactory.getLog(DefaultUserService.class);
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
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int delete(int no) {
        return userDao.delete(no);
    }
}
