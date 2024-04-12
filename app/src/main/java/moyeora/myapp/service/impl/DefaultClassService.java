package moyeora.myapp.service.impl;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.ClassDao;
import moyeora.myapp.service.ClassService;
import moyeora.myapp.vo.Class;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultClassService implements ClassService {

  private final ClassDao classDao;
  @Override
  public List<Class> findByDate(String date) {
    return classDao.findByDate(date);
  }

  @Override
  public List<Class> findByUserAddress(String address) {
    return classDao.findByUserAddress(address);
  }

  @Override
  public void add(Class clazz) {

    classDao.add(clazz);
  }

  @Override
  public List<Class> findBySchoolUser(int schoolNo) {
    return classDao.findBySchoolUser(schoolNo);
  }
}
