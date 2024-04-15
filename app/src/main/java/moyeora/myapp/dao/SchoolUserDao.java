package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.SchoolUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchoolUserDao {
  public SchoolUser findByUserNo(int no);

  List<SchoolUser> findBySchoolUserList(int no);

}