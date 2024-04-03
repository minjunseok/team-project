package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
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

        if(user.getTagNums() != null && user.getTagNums().size() >=3) {
            for(int tagNum : user.getTagNums()) {
                userTagDao.add(tagNum, user.getNo());
            }
        }
    }

}
