package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.User;
public interface UserService {

    void add(User user);

    User get(int no);

    List<User> list();

    User get(String email);

    String getEmail(String name, String phone);

    void delete(int no);

    List<String> findEmailByUserList(List<Integer> userList);

    void downGrade(List<Integer> userList);

    int update(User user);

    int pwdUpdate(User user);
}
