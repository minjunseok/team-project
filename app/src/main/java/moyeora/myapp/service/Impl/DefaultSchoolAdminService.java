package moyeora.myapp.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.dto.school.admin.MemberUpdateRequestDTO;
import moyeora.myapp.service.SchoolAdminService;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultSchoolAdminService implements SchoolAdminService {
  private final SchoolUserDao schoolUserDao;

  public List<SchoolUser> findUserBySchoolNo(int schoolNo,int levelNo) {
    return schoolUserDao.findUserBySchoolNo(schoolNo,levelNo);
  }

  public int levelUpdate(MemberUpdateRequestDTO memberUpdateRequestDTO) {
    int oldLevel = schoolUserDao.findLevel(memberUpdateRequestDTO);
    int level = oldLevel == 2 ? 3 : 2;
    memberUpdateRequestDTO.setLevelNo(level);
    schoolUserDao.updateLevel(memberUpdateRequestDTO);
    return level;
  }

  public void blackUpdate(MemberUpdateRequestDTO memberUpdateRequestDTO) {
    memberUpdateRequestDTO.setLevelNo(1);
    schoolUserDao.updateLevel(memberUpdateRequestDTO);
  }

  public void blackDelete(MemberUpdateRequestDTO memberUpdateRequestDTO) {
    schoolUserDao.deleteMember(memberUpdateRequestDTO);
  }
}
