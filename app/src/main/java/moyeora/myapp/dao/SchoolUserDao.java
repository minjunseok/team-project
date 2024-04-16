package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.dto.school.admin.MemberUpdateRequestDTO;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchoolUserDao {
  public SchoolUser findByUserNo(int no);

  public List<Integer> findSchoolNoByUserNo(int user);

  public List<SchoolUser> findUserBySchoolNo(int schoolNo, int levelNo);

  List<SchoolUser> findSubmitUserBySchoolNo(int no);

  List<SchoolUser> findBlackUserBySchoolNo(int no);

  int findLevel(MemberUpdateRequestDTO memberUpdateRequestDTO);

  void updateLevel(MemberUpdateRequestDTO memberUpdateRequestDTO);

  void deleteMember(MemberUpdateRequestDTO memberUpdateRequestDTO);
}