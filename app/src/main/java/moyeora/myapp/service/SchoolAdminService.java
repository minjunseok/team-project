package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.dto.school.admin.SchoolMemberUpdateRequestDTO;
import moyeora.myapp.dto.school.admin.SchoolOpenRangeUpdateRequestDTO;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;

public interface SchoolAdminService {

  List<SchoolUser> findUserBySchoolNo(int schoolNo, int levelNo);

  int levelUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  void blackUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  void deleteMember(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);
  void approveUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  School findBySchoolNo(int schoolNo);

  void updateSchoolOpenRange(SchoolOpenRangeUpdateRequestDTO schoolOpenRangeUpdateRequestDTO);
}
