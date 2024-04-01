package moyeora.myapp.dao;

import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

  void add(User user);

  int delete(int userNo);

  List<User> findAll(
          @Param("offset") int offset,
          @Param("rowCount") int rowCount);

  User findBy(int userNo);

  int update(User user);

  User findByEmailAndPwd(
          @Param("email") String email,
          @Param("pwd") String pwd);

  int countAll();
}
