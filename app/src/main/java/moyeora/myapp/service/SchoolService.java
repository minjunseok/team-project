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

  void add(School school);

  List<School> list(int categoryNo, int pageNo, int pageSize);

  School get(int schoolNo);

  int update(School school);

  int delete(int postNo);

  int countAll(int categoryNo);
  public List<School> findBySchoolName(String name);
  public void stopSchool(int no);
  public List<List<Integer>> findSchoolNoByUserNo(List<Integer> userList);

}
