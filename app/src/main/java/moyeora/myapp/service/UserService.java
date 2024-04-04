package moyeora.myapp.service;

import moyeora.myapp.vo.User;
import moyeora.myapp.vo.UserTag;

import java.util.List;

public interface UserService {

    void add(User user);

    User get(int no);

}
