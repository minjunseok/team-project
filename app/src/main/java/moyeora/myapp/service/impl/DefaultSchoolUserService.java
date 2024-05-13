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

//  @Override
//  public int findByUserLevelNo(int schoolNo, int userNo) {
//    return schoolUserDao.findByUserLevelNo(schoolNo, userNo);
//  }


  // 스쿨에 가입이 된 유저인지 확인하기 위한 코드
  @Override
  public int joinedSchoolUser(int userNo, int schoolNo) {
    return schoolUserDao.findBySchoolNo(userNo, schoolNo);
  }

  @Override
  public List<SchoolUser> findBySchoolUserList(int schoolNo) {
    return schoolUserDao.findBySchoolUserList(schoolNo);
  }

//  @Override
//  public void addSchoolUser(SchoolUser schoolUser) {
//    schoolUserDao.addSchoolUser(schoolUser);
//  }

  @Override
  public void addSchoolUser(int userNo, int schoolNo, int levelNo) {
    schoolUserDao.addSchoolUser(userNo, schoolNo, levelNo);
  }

  @Override
  public int findLevel(int schoolNo, int userNo) {
    return schoolUserDao.findLevel(schoolNo, userNo);
  }

  //  @Override
//  public void addSchoolUser(SchoolUser schoolUser) {
//    schoolUserDao.addSchoolUser(schoolUser);
//  }
}
