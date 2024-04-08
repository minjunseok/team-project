package moyeora.myapp.dao;

import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
     public void add(User user);

     public User findBy(int no);

     public void updateGrade(int no, int grade);

}
