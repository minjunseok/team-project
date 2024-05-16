package moyeora.myapp.service.impl;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolMemberDao;
import moyeora.myapp.service.SchoolMemberService;
import moyeora.myapp.vo.SchoolMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultSchoolMemberService implements SchoolMemberService {

  private final SchoolMemberDao schoolMemberDao;


  @Override
  public List<SchoolMember> list(int classNo) {
    return schoolMemberDao.findByClassMember(classNo);
  }


}
