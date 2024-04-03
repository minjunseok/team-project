package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

  public void add(User user);

  public int delete(int no);

  public List<User> findAll();

  public User findBy(int no);

  public int update(User member);

  public User findByEmailAndPassword(
      @Param("email") String email,
      @Param("password") String password);

  public String findByNameAndPhone(
      @Param("name")  String name,
      @Param("phone") String phone);
}
