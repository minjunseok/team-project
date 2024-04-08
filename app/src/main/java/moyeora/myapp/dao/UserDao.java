package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper
public interface UserDao {

  public void add(User user);

  public int delete(int no);

  public List<User> findAll();

  public User findByNo(int no);

  public int update(User member);

  public User findByEmail(@Param("email") String email);

  public String findByNameAndPhone(
      @Param("name")  String name,
      @Param("phone") String phone);

  public UserDetails getUserDetails(String username);
}
