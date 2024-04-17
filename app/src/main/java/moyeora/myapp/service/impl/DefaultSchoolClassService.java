package moyeora.myapp.service.impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolClassDao;
import moyeora.myapp.service.SchoolClassService;
import moyeora.myapp.vo.SchoolClass;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultSchoolClassService implements SchoolClassService {

  private final SchoolClassDao schoolClassDao;
  @Override
  public List<SchoolClass> findByDate(String date) {
    return schoolClassDao.findByDate(date);
  }

  @Override
  public List<SchoolClass> findByUserAddress(String address) {
    return schoolClassDao.findByUserAddress(address);
  }

  @Override
  public void add(SchoolClass clazz) {

    schoolClassDao.add(clazz);
  }

  @Override
  public List<SchoolClass> findBySchoolUser(int schoolNo) {
    return schoolClassDao.findBySchoolUser(schoolNo);
  }


  @Override
  public List<SchoolClass> schoolCalendarList(int schoolNo) {

    return schoolClassDao.schoolCalendarList(schoolNo);
  }
}
