package moyeora.myapp.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.dto.school.admin.SchoolMemberUpdateRequestDTO;
import moyeora.myapp.dto.school.admin.SchoolOpenRangeUpdateRequestDTO;
import moyeora.myapp.service.SchoolAdminService;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultSchoolAdminService implements SchoolAdminService {
  private final SchoolUserDao schoolUserDao;
  private final SchoolDao schoolDao;

  public List<SchoolUser> findUserBySchoolNo(int schoolNo,int levelNo) {
    return schoolUserDao.findUserBySchoolNo(schoolNo,levelNo);
  }

  public int levelUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    int oldLevel = schoolUserDao.findLevel(memberUpdateRequestDTO);
    int level = oldLevel == 2 ? 3 : 2;
    memberUpdateRequestDTO.setLevelNo(level);
    schoolUserDao.updateLevel(memberUpdateRequestDTO);
    return level;
  }

  public void blackUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    memberUpdateRequestDTO.setLevelNo(1);
    schoolUserDao.updateLevel(memberUpdateRequestDTO);
  }

  public void deleteMember(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    schoolUserDao.deleteMember(memberUpdateRequestDTO);
  }

  public void approveUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    memberUpdateRequestDTO.setLevelNo(2);
    schoolUserDao.updateLevel(memberUpdateRequestDTO);
  }

  public School findBySchoolNo(int schoolNo) {
    return schoolDao.findByNo(schoolNo);
  }

  public void updateSchoolOpenRange(SchoolOpenRangeUpdateRequestDTO schoolOpenRangeUpdateRequestDTO) {
    schoolDao.updateSchoolOpenRange(schoolOpenRangeUpdateRequestDTO);
  }
}
