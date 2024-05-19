package moyeora.myapp.dao;


import moyeora.myapp.dto.schoolclass.ClassDeleteDTO;
import moyeora.myapp.vo.SchoolClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SchoolClassDao {
  public List<SchoolClass> findByDate(String date,String str);

  public List<SchoolClass> findByUserAddress(String address);

  public void add(SchoolClass clazz);

  public List<SchoolClass> findBySchoolUser(int schoolNo);

  public List<SchoolClass> findBySchool(int schoolNo);

  SchoolClass findByNo(int classNo);

  void classDelete(ClassDeleteDTO classDeleteDTO);

  List<SchoolClass> findByWeek(String address,String weekString);
}
