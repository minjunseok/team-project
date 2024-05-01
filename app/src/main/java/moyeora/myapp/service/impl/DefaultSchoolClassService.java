package moyeora.myapp.service.impl;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.AlertDao;
import moyeora.myapp.dao.ClassUserDao;
import moyeora.myapp.dao.SchoolClassDao;
import moyeora.myapp.dao.SchoolMemberDao;
import moyeora.myapp.service.SchoolClassService;
import moyeora.myapp.vo.SchoolClass;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultSchoolClassService implements SchoolClassService {

  private final SchoolClassDao schoolClassDao;
  private final ClassUserDao classUserDao;
  private final SchoolMemberDao schoolMemberDao;
  private final AlertDao alertDao;

  @Override
  public List<SchoolClass> findByDate(String date) {
    return schoolClassDao.findByDate(date);
  }

  @Override
  public List<SchoolClass> findByUserAddress(String address) {
    return schoolClassDao.findByUserAddress(address);
  }

  @Override
  public void add(SchoolClass clazz) {

    schoolClassDao.insert(clazz);
    System.out.println("$$$$$$$$$$$$$$$$$$$" + clazz.getSchoolNo());
    System.out.println("$$$$$$$$$$$$$$$$$$$" + clazz.getUserNo());
    System.out.println("$$$$$$$$$$$$$$$$$$$" + clazz.getNo());
    System.out.println("=================================================");

    classUserDao.add(clazz.getUserNo(),  clazz.getNo(), clazz.getSchoolNo() );
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + clazz.getUserNo());
    System.out.println("$$$$$$$$$$$$$$$$$$$$444$$$" + clazz.getSchoolNo());
    System.out.println("#########################" + clazz.getNo());
  }


  @Override
  public List<SchoolClass> findBySchoolUser(int schoolNo) {
    return schoolClassDao.findBySchoolUser(schoolNo);
  }


  @Override
  public List<SchoolClass> schoolCalendarList(int schoolNo) {

    return schoolClassDao.findBySchool(schoolNo);
  }


  @Override
  public SchoolClass get(int classNo) {
    SchoolClass sc = schoolClassDao.findByNo(classNo);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    System.out.println(sc);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    return sc;

  }
}
