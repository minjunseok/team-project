package moyeora.myapp.dao;


import moyeora.myapp.vo.SchoolClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SchoolClassDao {
  public List<SchoolClass> findByDate(@Param("date") String date);

  public List<SchoolClass> findByUserAddress(String address);

  public void add(SchoolClass clazz);
  public List<SchoolClass> findBySchoolUser(int schoolNo);

  public List<SchoolClass> schoolCalendarList(int schoolNo);

}
