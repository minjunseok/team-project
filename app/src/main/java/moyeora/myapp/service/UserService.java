package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.User;

public interface UserService {

  void add(User user);

  List<User> list();

  User get(int no);

  User get(String email);

  String getEmail(String name, String phone);

  void update(User user);

  void delete(int no);

  User findByUsername(String username);

  User findByEmail(String email);
}
