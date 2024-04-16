package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.dto.school.admin.MemberUpdateRequestDTO;
import moyeora.myapp.vo.SchoolUser;

public interface SchoolAdminService {

  List<SchoolUser> findUserBySchoolNo(int schoolNo, int levelNo);

  int levelUpdate(MemberUpdateRequestDTO memberUpdateRequestDTO);

  void blackUpdate(MemberUpdateRequestDTO memberUpdateRequestDTO);

  void blackDelete(MemberUpdateRequestDTO memberUpdateRequestDTO);
}
