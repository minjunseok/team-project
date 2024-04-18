package moyeora.myapp.dao;
import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
     public void add(User user);

     public User findBy(int no);

     public int findUserGrade(int grade);


     public void updateGrade(int no, int grade);

     List<String> findEmailByUserList(List<Integer> userList);

     public void downGrade(List<Integer> userList);

  public int delete(int no);

  public List<User> findAll();

  public User findByNo(int no);

  public int update(User member);

  public User findByEmail(@Param("email") String email);

  public String findByNameAndPhone(
      @Param("name")  String name,
      @Param("phone") String phone);

  public List<User> findAllNoMaster(int limit);     public int pwdUpdate(User user);
}
