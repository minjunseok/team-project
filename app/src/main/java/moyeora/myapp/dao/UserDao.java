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

//  public List<User> findAll(
//      @Param("offset") int offset,
//      @Param("rowCount") int rowCount);

  public User findBy(int no);

  public int update(User member);

  public User findByEmailAndPassword(
      @Param("email") String email,
      @Param("password") String password);

//  int countAll();
}
