package moyeora.myapp.service;

import moyeora.myapp.vo.SchoolClass;

import java.util.List;

public interface SchoolClassService {

  public List<SchoolClass> findBySchoolUser(int schoolNo);
  public List<SchoolClass> findByDate(String date);

  public List<SchoolClass> findByUserAddress(String address);

  void add(SchoolClass clazz);

  SchoolClass get(int no);

  public List<SchoolClass> schoolCalendarList(int schoolNo);
}
