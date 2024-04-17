package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

  void add(User user);

  void save(User user);

  int delete(int no);

  List<User> findAll();

  User findByNo(int no);

  int update(User user);

  int updatePassword(User user);

  User findByEmail(String email);

  User findOAuth2User(String email, String provider);

  String findByNameAndPhone(
      @Param("name") String name,
      @Param("phone") String phone);
}
