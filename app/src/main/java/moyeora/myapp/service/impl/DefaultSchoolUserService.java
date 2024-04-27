package moyeora.myapp.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.vo.Level;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@RequiredArgsConstructor
public class DefaultSchoolUserService implements SchoolUserService {

  private final SchoolUserDao schoolUserDao;



  @Override
  public List<SchoolUser> findBySchoolUserList(int schoolNo) {
    return schoolUserDao.findBySchoolUserList(schoolNo);
  }

//  @Override
//  public void addSchoolUser(SchoolUser schoolUser) {
//    schoolUserDao.addSchoolUser(schoolUser);
//  }

  @Override
  public void addSchoolUser(int userNo, int schoolNo) {
    schoolUserDao.addSchoolUser(userNo, schoolNo);
  }


//  @Override
//  public void addSchoolUser(SchoolUser schoolUser) {
//    schoolUserDao.addSchoolUser(schoolUser);
//  }
}
