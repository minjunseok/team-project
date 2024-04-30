package moyeora.myapp.service.impl;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.AlertDao;
import moyeora.myapp.dao.ClassUserDao;
import moyeora.myapp.dao.SchoolClassDao;
import moyeora.myapp.dao.SchoolMemberDao;
import moyeora.myapp.service.SchoolClassService;
import moyeora.myapp.vo.Alert;
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

    schoolClassDao.add(clazz);
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
  public void addAlert(Alert alert, SchoolClass clazz) {
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@" + alert.getUserNo());
    System.out.println("$$$$$$$$$$$$$$$$$$$" + alert.getToUserNo());
    System.out.println("==============================================");
    clazz.getNo();
    clazz.getContent();
    clazz.getName();
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@" + clazz.getNo());
    System.out.println("$$$$$$$$$$$$$$$$$$$" + clazz.getContent());
    System.out.println("$$$$$$$$$$$$$$$$$$$" + clazz.getName());
    System.out.println("==============================================");
    alertDao.addAlert(alert);

  }

  @Override
  public List<SchoolClass> findBySchoolUser(int schoolNo) {
    return schoolClassDao.findBySchoolUser(schoolNo);
  }


  @Override
  public List<SchoolClass> schoolCalendarList(int schoolNo) {

    return schoolClassDao.schoolCalendarList(schoolNo);
  }

  @Override
  public SchoolClass get(int no) {
    return null;
  }
}
