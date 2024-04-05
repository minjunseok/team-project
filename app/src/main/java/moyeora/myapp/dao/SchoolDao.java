package moyeora.myapp.dao;


import java.util.List;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SchoolDao {

  public List<School> findHotSchool(int category);

  public List<School> findBySchoolName(@Param("name") String name);

}
