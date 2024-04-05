package moyeora.myapp.service;


import java.util.List;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.Tag;
import org.springframework.ui.Model;

public interface SchoolService {

  public SchoolUser findByUserNo(int no);

  public List<String> findWeek();

  public List<School> findHotSchool(int category);

  public List<School> findBySchoolName(String name);
}
