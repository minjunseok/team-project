package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.vo.School;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultSchoolService implements SchoolService {

  private final SchoolDao schoolDao;

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
