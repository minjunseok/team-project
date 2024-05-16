package moyeora.myapp.service;

import moyeora.myapp.dto.school.admin.SchoolMemberUpdateRequestDTO;
import moyeora.myapp.dto.school.admin.SchoolOpenRangeUpdateRequestDTO;
import moyeora.myapp.dto.schoolclass.ClassDeleteDTO;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;

import java.util.List;

public interface SchoolAdminService {

  List<SchoolUser> findUserBySchoolNo(int schoolNo, int levelNo);

  int levelUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);


  void blackUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  void deleteMember(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);
  void approveUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO);

  School findBySchoolNo(int schoolNo);

  void updateSchoolOpenRange(SchoolOpenRangeUpdateRequestDTO schoolOpenRangeUpdateRequestDTO);
  int authAdmin(int userNo, int schoolNo);

  int authSubAdmin(int userNo, int schoolNo);


  School getSchool(int schoolNo);

  int update(School school);

  int isNameExists(String name);

  int deleteSchool(int schoolNo);

  School getSchoolNo(int schoolNo);


}