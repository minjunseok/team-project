package moyeora.myapp.service;

import moyeora.myapp.dto.schoolclass.ClassDeleteDTO;
import moyeora.myapp.dto.schoolclass.SchoolClassRequestDTO;
import moyeora.myapp.vo.SchoolClass;
import moyeora.myapp.vo.User;

import java.util.List;

public interface  SchoolClassService {

  public List<SchoolClass> findBySchoolUser(int schoolNo);
  public List<SchoolClass> findByDate(String date, int userNo);

  public List<SchoolClass> findByUserAddress(String address);

  void add(SchoolClass clazz);

  SchoolClass get(int no);

  public List<SchoolClass> schoolCalendarList(int schoolNo);

  void insert(SchoolClassRequestDTO schoolClassRequestDTO);

  boolean isMember(int classNo, int userNo);

  void memberDelete(SchoolClassRequestDTO schoolClassRequestDTO);

  void classDelete(ClassDeleteDTO classDeleteDTO);

  List<SchoolClass> weekClass(String address, List<String> week);
  int classUpdate(SchoolClass clazz);

}
