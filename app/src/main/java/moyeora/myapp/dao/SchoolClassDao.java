package moyeora.myapp.dao;


import java.util.List;

import moyeora.myapp.vo.SchoolClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface SchoolClassDao {
  public List<SchoolClass> findByDate(@Param("date") String date);

  public List<SchoolClass> findByUserAddress(String address);

  public void add(SchoolClass clazz);
  public List<SchoolClass> findBySchoolUser(int schoolNo);

  public List<SchoolClass> schoolCalendarList(int schoolNo);



}
