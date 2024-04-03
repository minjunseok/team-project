package moyeora.myapp.service;


import java.util.List;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.Tag;

public interface SchoolService {

  public SchoolUser findByUserNo(int no);

  public List<String> findWeek();

  public List<School> findHotSchool(int category);


}
