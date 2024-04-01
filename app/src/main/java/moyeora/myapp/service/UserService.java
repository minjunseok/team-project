package moyeora.myapp.service;

import moyeora.myapp.vo.User;

import java.util.List;

public interface UserService {

    void add(User user);

    List<User> list();

    User get(int no);

    int update(User user);

    int delete(int no);

}
