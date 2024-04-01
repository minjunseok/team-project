
package moyeora.myapp.service.Impl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.vo.School;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultSchoolService implements SchoolService {

  private final SchoolUserDao schoolUserDao;
   private final SchoolDao schoolDao;


  public SchoolUser findByUserNo(int no) {
    return schoolUserDao.findByUserNo(no);

  }

  @Override
  public List<String> findWeek() {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
    DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    List<String> list = new ArrayList<>();
    for(int i = 0; i<7; i++) {
      list.add(dateformat.format(c.getTime()));
      c.add(Calendar.DAY_OF_YEAR,1);
    }
    return list;


  @Transactional
  @Override
  public void add(School school) {
    schoolDao.add(school);
  }

  @Override
  public List<School> list(int categoryNo, int pageNo, int pageSize) {
    return schoolDao.findAll(categoryNo, pageSize * (pageNo - 1), pageSize);
  }

  @Override
  public School get(int schoolNo) {
    return schoolDao.findBy(schoolNo);
  }

  @Transactional
  @Override
  public int update(School school) {
    int count = schoolDao.update(school);
    return count;
  }

  @Transactional
  @Override
  public int delete(int postNo) {
    return schoolDao.delete(postNo);
  }

  @Override
  public int countAll(int categoryNo) {
    return schoolDao.countAll(categoryNo);
  }
}
