package moyeora.myapp.dao;

import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
     public void add(User user);

     public User findBy(int no);

     public int findUserGrade(int grade);


}
