package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.dto.school.admin.SchoolMemberUpdateRequestDTO;
import moyeora.myapp.vo.SchoolUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SchoolUserDao {

    int findByUserLevelNo(int schoolNo, int userNo);


    public SchoolUser findByUserNo(int no);

    public void insert(SchoolUser user);

    public int schoolUserLevelCount(int no);

    List<SchoolUser> findBySchoolUserList(int schoolNo);


    public List<Integer> findSchoolNoByUserNo(int user);

    public List<SchoolUser> findUserBySchoolNo(int schoolNo, int levelNo);

    List<SchoolUser> findSubmitUserBySchoolNo(int no);

    List<SchoolUser> findBlackUserBySchoolNo(int no);



  int findLevel(int schoolNo, int userNo); //민준 사용

  int findLevel(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);


    void updateLevel(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

    void deleteMember(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  void disableForeignKeyChecks(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  void enableForeignKeyChecks(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

    int findAdmin(int userNo, int schoolNo);

    int findSubAdmin(int userNo, int schoolNo);

    public void addSchoolUser(int userNo, int schoolNo, int levelNo);

    int deleteSchoolUsers(int schoolNo);


    // 스쿨에 가입이 되어있는지 안되어있는지 확인하기 위한 코드
    int findByMemberCheck(int schoolNo, int userNo);


  List<Integer> findMasterByUserNo(int userNo);
  void forcedDrop(int schoolNo);
}