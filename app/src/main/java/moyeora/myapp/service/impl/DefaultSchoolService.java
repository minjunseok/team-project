package moyeora.myapp.service.impl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class DefaultSchoolService implements SchoolService {

  private final SchoolDao schoolDao;
  private final SchoolUserDao schoolUserDao;


  public SchoolUser findByUserNo(int no) {
    return schoolUserDao.findByUserNo(no);

  }

  @Override
  public List<String> findWeek() {
    Calendar c = Calendar.getInstance();
    //c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
    DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    List<String> list = new ArrayList<>();
    for(int i = 0; i<7; i++) {
      list.add(dateformat.format(c.getTime()));
      c.add(Calendar.DAY_OF_YEAR,1);
    }
    return list;
  }

  @Override
  public List<School> findHotSchool(int category) {

    return schoolDao.findHotSchool(category);
  }

  @Override
  public void add(School school) {
  }

  @Override
  public List<School> list(int categoryNo, int pageNo, int pageSize) {
    return null;
  }

  @Override
  public School get(int schoolNo) {
    return null;
  }

  @Override
  public int update(School school) {
    return 0;
  }

  @Override
  public int delete(int postNo) {
    return 0;
  }

  @Override
  public int countAll(int categoryNo) {
    return 0;
  }
  @Override
  public List<School> findBySchoolName(String name) {
    return schoolDao.findBySchoolName(name);
  }

  public List<List<Integer>> findSchoolNoByUserNo(List<Integer> userList) {
    List<List<Integer>> arr = new ArrayList<>();
    for(int user : userList) {
      arr.add(schoolUserDao.findSchoolNoByUserNo(user));
    }
    return arr;
  }

  public void stopSchool(int schoolNo) {
    schoolDao.stopSchool(schoolNo);
  }
}
