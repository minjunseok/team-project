package moyeora.myapp.dao;

import java.util.List;

import moyeora.myapp.vo.School;
import moyeora.myapp.dto.school.admin.SchoolMemberUpdateRequestDTO;
import moyeora.myapp.vo.SchoolUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchoolUserDao {
  public SchoolUser findByUserNo(int no);

  List<SchoolUser> findBySchoolUserList(int schoolNo);


  public List<Integer> findSchoolNoByUserNo(int user);

  public List<SchoolUser> findUserBySchoolNo(int schoolNo, int levelNo);

  List<SchoolUser> findSubmitUserBySchoolNo(int no);

  List<SchoolUser> findBlackUserBySchoolNo(int no);

  int findLevel(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  void updateLevel(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  void deleteMember(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  int findAdmin(int userNo, int schoolNo);
  int findSubAdmin(int userNo, int schoolNo);
}