package moyeora.myapp.dao;

import moyeora.myapp.vo.SchoolUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SchoolUserDao {
  public SchoolUser findByUserNo(int no);

  public void insert(SchoolUser user);

  public int schoolUserLevelCount(int no);
}