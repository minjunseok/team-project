package moyeora.myapp.dao;


import java.util.List;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchoolDao {
  public SchoolUser findByUserNo(int no);

  public List<School> findHotSchool(int category);


}
