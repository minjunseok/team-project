package moyeora.myapp.service;


import moyeora.myapp.vo.School;
import java.util.List;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.Tag;

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
}
