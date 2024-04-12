package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.Class;

public interface ClassService {

  public List<Class> findBySchoolUser(int schoolNo);
  public List<Class> findByDate(String date);

  public List<Class> findByUserAddress(String address);

  void add(Class clazz);


}
