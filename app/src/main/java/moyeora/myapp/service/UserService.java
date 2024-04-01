package moyeora.myapp.service;

import moyeora.myapp.vo.User;

import java.util.List;

public interface UserService {

  void add(User user);

  List<User> list(int pageNo, int pageSize);

  User get(int userNo);

  User get(String email, String pwd);

  int update(User user);

  int delete(int userNo);

  int countAll();
}
