package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.dao.UserDao;
//import moyeora.myapp.dao.UserTagDao;
import moyeora.myapp.dao.UserTagDao;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {
    private static final Log log = LogFactory.getLog(DefaultUserService.class);
    private final UserDao userDao;
    private final UserTagDao userTagDao;
    private final SchoolUserDao schoolUserDao;

    @Override
    public void add(User user) {
        userDao.add(user);

        if(user.getTagNums() != null && user.getTagNums().size() >=3) {
            for(int tagNum : user.getTagNums()) {
                userTagDao.add(tagNum, user.getNo());
            }
        }
    }

    @Override
    public User get(int no) {
      return userDao.findBy(no);
    }

    @Override
    public int findUserGrade(int grade) {
        return userDao.findUserGrade(grade);
    }
}
