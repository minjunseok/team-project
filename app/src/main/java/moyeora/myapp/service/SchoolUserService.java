package moyeora.myapp.service;

import java.util.List;

import moyeora.myapp.vo.Level;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.User;

public interface SchoolUserService {


  List<SchoolUser> findBySchoolUserList(int schoolNo);

  int findByUserLevelNo(int schoolNo, int userNo);

//  void addSchoolUser (SchoolUser schoolUser);
  void addSchoolUser (int userNo, int schoolNo, int levelNo);
  }


