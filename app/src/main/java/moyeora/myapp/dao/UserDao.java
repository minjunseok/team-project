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

  public List<User> findAllNoMaster(int limit);
  public int passwordUpdate(User user);
}
