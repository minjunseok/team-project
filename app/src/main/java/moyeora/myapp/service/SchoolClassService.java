package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.SchoolClass;

public interface SchoolClassService {

  public List<SchoolClass> findBySchoolUser(int schoolNo);
  public List<SchoolClass> findByDate(String date);

  public List<SchoolClass> findByUserAddress(String address);

  void add(SchoolClass clazz);

  public List<SchoolClass> schoolCalendarList(int schoolNo);
}
