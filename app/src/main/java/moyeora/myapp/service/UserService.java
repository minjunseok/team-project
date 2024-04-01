package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.User;

public interface UserService {

  void add(User user);

//  List<User> list(int pageNo, int pageSize);

  List<User> list();

  User get(int no);

  User get(String email, String password);

  int update(User user);

  int delete(int no);

//  int countAll();
}
