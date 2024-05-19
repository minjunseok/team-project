package moyeora.myapp.service;


import moyeora.myapp.vo.User;

import java.util.List;

public interface UserService {

  void add(User user);

  void save(User user);

    User get(int no);

    List<User> list();

    User get(String email);

    String getEmail(String name, String phone);

  int updatePassword(User user);

    void delete(int no);

  User findOAuth2User(String email, String provider);

  User findByEmail(String email);

    List<String> findEmailByUserList(List<Integer> userList);

    void downGrade(List<Integer> userList);

    int update(User user);


  int passwordUpdate(User user);

    User getUserInfo(int no);

  public String getAddress(int userNo);
}
