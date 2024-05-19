package moyeora.myapp.service;

import moyeora.myapp.vo.SchoolUser;

import java.util.List;

public interface SchoolUserService {


        List<SchoolUser> findBySchoolUserList(int schoolNo);


    //  int findByUserLevelNo(int schoolNo, int userNo); 미사용

      int findLevel(int schoolNo, int userNo);

    //  void addSchoolUser (SchoolUser schoolUser);

      void addSchoolUser (int userNo, int schoolNo, int levelNo);


      // 스쿨에 가입이 된 유저인지 확인하기 위한 코드
      int findByMemberCheck(int schoolNo, int useNo);



      int findByUserLevelNo(int schoolNo, int userNo);


}


