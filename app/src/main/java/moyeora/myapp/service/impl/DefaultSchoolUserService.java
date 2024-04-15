package moyeora.myapp.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@RequiredArgsConstructor
public class DefaultSchoolUserService implements SchoolUserService {

  private final SchoolUserDao schoolUserDao;


  @Transactional
  @Override
  public List<SchoolUser> findBySchoolUserList(int no) {
    return schoolUserDao.findBySchoolUserList(no);
  }
}
