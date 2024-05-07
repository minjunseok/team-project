package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.SchoolUser;

public interface SchoolUserService {


  List<SchoolUser> findBySchoolUserList(int schoolNo);

  int findByUserLevelNo(int schoolNo, int userNo);

  }


