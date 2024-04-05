package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.TagDao;
import moyeora.myapp.dao.UserDao;
//import moyeora.myapp.dao.UserTagDao;
import moyeora.myapp.dao.UserTagDao;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.Tag;
import moyeora.myapp.vo.User;
import moyeora.myapp.vo.UserTag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private static final Log log = LogFactory.getLog(DefaultUserService.class);
    private final UserDao userDao;
    private final UserTagDao userTagDao;
    private final TagDao tagDao;

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
        User user = userDao.findBy(no);
        System.out.println("user count :::::" + user.getTags().size());
        return user;
    }
}
