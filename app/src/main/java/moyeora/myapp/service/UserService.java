package moyeora.myapp.service;

import moyeora.myapp.vo.User;
import moyeora.myapp.vo.UserTag;

import java.util.List;

public interface UserService {

    void add(User user);

    List<User> list();

    User get(int no);

    int update(User user);

    int delete(int no);

//   List<UserTag> getUserTag(int userNo);
//
//   int deleteUserTag(int userNo);
}
