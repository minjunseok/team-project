package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.*;
import moyeora.myapp.dto.school.admin.SchoolMemberUpdateRequestDTO;
import moyeora.myapp.dto.school.admin.SchoolOpenRangeUpdateRequestDTO;
import moyeora.myapp.service.SchoolAdminService;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DefaultSchoolAdminService implements SchoolAdminService {
  private final SchoolUserDao schoolUserDao;
  private final SchoolDao schoolDao;
  private final SchoolTagDao schoolTagDao;
  private final SchoolClassDao schoolClassDao;
  private final PostDao postDao;

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
  //

  public void blackUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    schoolUserDao.updateLevel(memberUpdateRequestDTO);
  }

  public void deleteMember(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {

    schoolUserDao.deleteMember(memberUpdateRequestDTO);
    schoolUserDao.disableForeignKeyChecks(memberUpdateRequestDTO);
    schoolUserDao.enableForeignKeyChecks(memberUpdateRequestDTO);
  }

  public void approveUpdate(SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    //memberUpdateRequestDTO.setLevelNo(2);
    schoolUserDao.updateLevel(memberUpdateRequestDTO);
  }

  public School findBySchoolNo(int schoolNo) {
    return schoolDao.findByNo(schoolNo);
  }

  public void updateSchoolOpenRange(SchoolOpenRangeUpdateRequestDTO schoolOpenRangeUpdateRequestDTO) {
    schoolDao.updateSchoolOpenRange(schoolOpenRangeUpdateRequestDTO);
  }

  public int authAdmin(int userNo, int schoolNo) {
    return schoolUserDao.findAdmin(userNo, schoolNo);
  }

  public int authSubAdmin(int userNo, int schoolNo) {
    return schoolUserDao.findSubAdmin(userNo, schoolNo);
  }

  @Override
  public School getSchool(int schoolNo) {
    return schoolDao.findBySchool(schoolNo);
  }

  @Override
  public int update(School school) {

    if (school.getName() == null || school.getName().isEmpty()) {
      throw new IllegalArgumentException("스쿨명을 입력하세요.");
    }
    if (school.getTagNums() != null) {
      schoolDao.update(school);
      for (int tagNum : school.getTagNums()) {
        schoolTagDao.add(tagNum, school.getNo());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + tagNum);
      }

      if (school.getName() == null || school.getName().isEmpty()) {
        throw new IllegalArgumentException("스쿨명을 입력하세요.");
      }
    }
    return schoolDao.update(school);
  }

  @Override
  public int isNameExists(String name) {
    return schoolDao.isNameExists(name);
  }



  @Override
  public int deleteSchool(int schoolNo) {
    int rowsAffected = 0;
    //  해당 스쿨 GM 삭제
    rowsAffected += schoolDao.deleteSchoolGm(schoolNo);

    // 해당 스쿨 태그 삭제
    rowsAffected += schoolTagDao.deleteSchoolTags(schoolNo);

    // 해당 스쿨 클래스 유저 삭제
    rowsAffected += schoolClassDao.deleteAllSchoolClassUser(schoolNo);

    // 해당 스쿨 클래스 삭제
    rowsAffected += schoolClassDao.deleteAllSchoolClass(schoolNo);

    // 해당 스쿨 댓글 삭제
    rowsAffected += postDao.deleteAllSchoolComments(schoolNo);

    // 해당 스쿨 포스트 삭제
    rowsAffected += postDao.deleteAllSchoolPost(schoolNo);

    // 해당 학교 유저 삭제
    rowsAffected += schoolUserDao.deleteSchoolUsers(schoolNo);

    // 해당 학교 삭제
    rowsAffected += schoolDao.deleteSchool(schoolNo);

    return rowsAffected;
  }

  //스쿨 이름,넘버 가져오는 메서드
  @Override
  public School getSchoolNo(int schoolNo) {
    return schoolDao.findBySchoolNo(schoolNo);
  }
}