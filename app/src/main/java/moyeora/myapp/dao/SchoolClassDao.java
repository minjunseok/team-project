package moyeora.myapp.dao;


import moyeora.myapp.dto.schoolclass.ClassDeleteDTO;
import moyeora.myapp.vo.SchoolClass;
import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SchoolClassDao {
  public List<SchoolClass> findByDate(@Param("date") String date);

  public List<SchoolClass> findByUserAddress(String address);

  public void add(SchoolClass clazz);

  public List<SchoolClass> findBySchoolUser(int schoolNo);

  public List<SchoolClass> findBySchool(int schoolNo);

  SchoolClass findByNo(int classNo);

  void classDelete(ClassDeleteDTO classDeleteDTO);

  int classUpdate(SchoolClass clazz);


  // 해당스쿨의 클래스를 다 지움
  int deleteAllSchoolClass(int schoolNo);

  int deleteAllSchoolClassUser(int schoolNo);
}
