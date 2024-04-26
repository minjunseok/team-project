package moyeora.myapp.service;

import java.util.List;

import moyeora.myapp.vo.Level;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.User;

public interface SchoolUserService {


  List<SchoolUser> findBySchoolUserList(int schoolNo);

//  void addSchoolUser (SchoolUser schoolUser);
  void addSchoolUser (int userNo);
  }


